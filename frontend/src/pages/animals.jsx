import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import api from '../services/api'
import '../styles/animals.css'

function Animals() {
  // Animals list by API
  const [animais, setAnimais] = useState([])
  // Loading state
  const [loading, setLoading] = useState(true)

  // Filters list state
  const [search, setSearch] = useState('')
  const [especieFilter, setEspecieFilter] = useState('')
  const [habitatFilter, setHabitatFilter] = useState('')
  const [countryFilter, setCountryFilter] = useState('')

  // Modal control
  const [showModal, setShowModal] = useState(false)
  // Edit/Create mode
  const [isEditMode, setIsEditMode] = useState(false)
  // Store animal ID being edited
  const [editingAnimalId, setEditingAnimalId] = useState(null)

  // Modal form data
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    birthDate: '',
    speciesId: '',
    habitatId: '',
    countryId: '',
    registrationDate: new Date().toISOString().split('T')[0]
  })

  // Loaded lists for modal <selects>
  const [species, setSpecies] = useState([])
  const [habitats, setHabitats] = useState([])
  const [countries, setCountries] = useState([])

  // Fetch animals by API data with optional filters
  const fetchAnimais = async () => {
    try {
      const params = {}
      if (search.trim()) params.name = search.trim()
      const res = await api.get('/animals', { params })
      setAnimais(res.data)
      setLoading(false)
    } catch (err) {
      console.error('Error loading animals:', err)
      alert('Erro ao carregar animais!')
      setLoading(false)
    }
  }

  // Load reference list only when modal is open
  useEffect(() => {
    if (showModal) {
      const loadReferences = async () => {
        try {
          const [speciesRes, habitatsRes, countriesRes] = await Promise.all([
            api.get('/species'),
            api.get('/habitats'),
            api.get('/countries')
          ])
          setSpecies(speciesRes.data)
          setHabitats(habitatsRes.data)
          setCountries(countriesRes.data)
        } catch (err) {
          alert('Erro ao carregar opções')
        }
      }
      loadReferences()
    }
  }, [showModal])

  // Reload animals list when text filter changes
  useEffect(() => {
    fetchAnimais()
  }, [search])

  // Apply local filters in animals loaded list
  const animaisFiltrados = animais.filter(animal => {
    const matchEspecie = !especieFilter || animal.species?.name === especieFilter
    const matchHabitat = !habitatFilter || animal.habitat?.name === habitatFilter
    const matchCountry = !countryFilter || animal.country?.name === countryFilter
    return matchEspecie && matchHabitat && matchCountry
  })

  // Opens the creation modal
  const openCreateModal = () => {
    setIsEditMode(false)
    setEditingAnimalId(null)
    // Clears form
    setFormData({
      name: '',
      description: '',
      birthDate: '',
      speciesId: '',
      habitatId: '',
      countryId: '',
      registrationDate: new Date().toISOString().split('T')[0]
    })
    setShowModal(true)
  }

  // Opens the edit modal with animal data by API
  const openEditModal = (animal) => {
    setIsEditMode(true)
    setEditingAnimalId(animal.id)
    setFormData({
      name: animal.name || '',
      description: animal.description || '',
      birthDate: animal.birthDate || '',
      speciesId: animal.species?.id?.toString() || '',
      habitatId: animal.habitat?.id?.toString() || '',
      countryId: animal.country?.id?.toString() || '',
      registrationDate: animal.registrationDate || ''
    })
    setShowModal(true)
  }

  // Deletes animal
  const handleDelete = async (id, name) => {
    if (!window.confirm(`Tem certeza que deseja excluir o animal "${name}"?`)) return

    try {
      await api.delete(`/animals/${id}`)
      alert('Animal excluído com sucesso!')
      fetchAnimais()
    } catch (err) {
      alert('Erro ao excluir animal')
    }
  }

  // Valid the birthdate
  const handleBirthDateChange = (e) => {
    const selectedDate = e.target.value
    const today = new Date().toISOString().split('T')[0]

    if (selectedDate > today) {
      alert('A data de nascimento não pode ser no futuro!')
      return
    }

    setFormData({ ...formData, birthDate: selectedDate })
  }

  // Submit creation/edit form
  const handleSubmit = async (e) => {
    e.preventDefault()

    // Valid data
    if (!formData.name || !formData.description || !formData.speciesId || !formData.habitatId) {
      alert('Preencha nome, descrição, espécie e habitat!')
      return
    }

    // Valid birthdate
    if (formData.birthDate) {
      const today = new Date().toISOString().split('T')[0]
      if (formData.birthDate > today) {
        alert('A data de nascimento não pode ser no futuro!')
        return
      }
    }

    try {
      // Format sent to API
      const payload = {
        name: formData.name.trim(),
        description: formData.description.trim(),
        birthDate: formData.birthDate || null,
        speciesId: parseInt(formData.speciesId),
        habitatId: parseInt(formData.habitatId),
        birthPlaceId: formData.countryId ? parseInt(formData.countryId) : null
      }

      // Update mode
      if (isEditMode) {
        await api.put(`/animals/${editingAnimalId}`, payload)
        alert('Animal atualizado com sucesso!')
      }
      // Create mode
      else {
        await api.post('/animals', payload)
        alert('Animal cadastrado com sucesso!')
      }

      setShowModal(false)
      fetchAnimais()
    } catch (err) {
      console.error(err.response?.data || err)
      alert(isEditMode ? 'Erro ao atualizar animal' : 'Erro ao cadastrar animal')
    }
  }

  return (
    <>
      <header>
        <Link to="/"><h1>Gerenciamento do Zoológico</h1></Link>
      </header>

      <main>
        <div className="search-bar">
          <input
            type="text"
            placeholder="Pesquisar por nome..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />

          <select value={especieFilter} onChange={(e) => setEspecieFilter(e.target.value)}>
            <option value="">Todas as Espécies</option>
            {[...new Set(animais.map(a => a.species?.name).filter(Boolean))].map(esp => (
              <option key={esp} value={esp}>{esp}</option>
            ))}
          </select>

          <select value={habitatFilter} onChange={(e) => setHabitatFilter(e.target.value)}>
            <option value="">Todos os Habitats</option>
            {[...new Set(animais.map(a => a.habitat?.name).filter(Boolean))].map(hab => (
              <option key={hab} value={hab}>{hab}</option>
            ))}
          </select>

          <select value={countryFilter} onChange={(e) => setCountryFilter(e.target.value)}>
            <option value="">Todos os Países</option>
            <option value="">Desconhecido</option>
            {[...new Set(animais.map(a => a.country?.name).filter(Boolean))].map(country => (
              <option key={country} value={country}>{country}</option>
            ))}
          </select>

          <button className="new-registration" onClick={openCreateModal}>
            Novo Animal
          </button>
        </div>

        <table className="animal-table">
          <thead>
            <tr>
              <th>Nome</th>
              <th>Data de Nascimento</th>
              <th>Espécie</th>
              <th>Habitat</th>
              <th>País de Origem</th>
              <th>Data de Entrada</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr><td colSpan="7" style={{textAlign:'center', padding:'60px'}}>Carregando...</td></tr>
            ) : animaisFiltrados.length === 0 ? (
              <tr><td colSpan="7" style={{textAlign:'center', padding:'60px', color:'#888'}}>Nenhum animal encontrado</td></tr>
            ) : (
              animaisFiltrados.map(animal => (
                <tr key={animal.id}>
                  <td><strong>{animal.name}</strong></td>
                  <td>{animal.birthDate || '—'}</td>
                  <td>{animal.species?.name || '—'}</td>
                  <td>{animal.habitat?.name || '—'}</td>
                  <td>{animal.country?.name || '—'}</td>
                  <td>{animal.registrationDate || '—'}</td>
                  <td>
                    <button className="edit-button" onClick={() => openEditModal(animal)}>
                      Editar
                    </button>
                    <button className="delete-button" onClick={() => handleDelete(animal.id, animal.name)}>
                      Excluir
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </main>

      {showModal && (
        <div className="modal-overlay" onClick={() => setShowModal(false)}>
          <div className="modal-content" onClick={e => e.stopPropagation()}>
            <h2 className="modal-title">
              {isEditMode ? 'Editar Animal' : 'Novo Animal'}
            </h2>
            <form onSubmit={handleSubmit} className="data-container">

              <div className="data-item">
                <span className="data-label">Nome do Animal:</span>
                <input
                  type="text"
                  className="data-input"
                  required
                  value={formData.name}
                  onChange={e => setFormData({...formData, name: e.target.value})}
                />
              </div>

              <div className="data-item">
                <span className="data-label">Descrição:</span>
                <textarea
                  className="data-input"
                  rows="4"
                  required
                  value={formData.description}
                  onChange={e => setFormData({...formData, description: e.target.value})}
                />
              </div>

              <div className="data-item">
                <span className="data-label">Data de Nascimento:</span>
                <input
                  type="date"
                  className="data-input"
                  value={formData.birthDate}
                  onChange={handleBirthDateChange}
                  max={new Date().toISOString().split('T')[0]}
                />
              </div>

              <div className="data-item">
                <span className="data-label">Espécie:</span>
                <select
                  className="data-input"
                  required
                  value={formData.speciesId}
                  onChange={e => setFormData({...formData, speciesId: e.target.value})}
                >
                  <option value="">Selecione a espécie</option>
                  {species.map(s => (
                    <option key={s.id} value={s.id}>{s.name}</option>
                  ))}
                </select>
              </div>

              <div className="data-item">
                <span className="data-label">Habitat:</span>
                <select
                  className="data-input"
                  required
                  value={formData.habitatId}
                  onChange={e => setFormData({...formData, habitatId: e.target.value})}
                >
                  <option value="">Selecione o habitat</option>
                  {habitats.map(h => (
                    <option key={h.id} value={h.id}>{h.name}</option>
                  ))}
                </select>
              </div>

              <div className="data-item">
                <span className="data-label">País de Origem:</span>
                <select
                  className="data-input"
                  value={formData.countryId}
                  onChange={e => setFormData({...formData, countryId: e.target.value})}
                >
                  <option value="">Desconhecido</option>
                  {countries.map(c => (
                    <option key={c.id} value={c.id}>{c.name}</option>
                  ))}
                </select>
              </div>

              <div className="button-group">
                <button type="submit" className="save-button">
                  {isEditMode ? 'Atualizar' : 'Salvar'}
                </button>
                <button type="button" className="cancel-button" onClick={() => setShowModal(false)}>
                  Cancelar
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </>
  )
}

export default Animals