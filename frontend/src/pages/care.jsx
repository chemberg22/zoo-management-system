import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import api from '../services/api'
import '../styles/animals.css'

function Cares() {
  // Animals list by API
  const [animals, setAnimals] = useState([])
  // Loading state
  const [loading, setLoading] = useState(true)
  // Search field state
  const [search, setSearch] = useState('')

  // Selected animal
  const [selectedAnimal, setSelectedAnimal] = useState(null)
  // Care list by specific animal
  const [animalCares, setAnimalCares] = useState([])

  // Modal control creation/edit
  const [showCareModal, setShowCareModal] = useState(false)
  const [isEditCare, setIsEditCare] = useState(false)
  const [editingCareId, setEditingCareId] = useState(null)

  // Care form data
  const [careForm, setCareForm] = useState({
    careTypeId: '',
    realizationDate: new Date().toISOString().split('T')[0],
    observations: ''
  })

  // Animal care list <select>
  const [careTypes, setCareTypes] = useState([])

  // Fetch animals with API data with name filter
  const fetchAnimals = async () => {
    try {
      const params = search.trim() ? { name: search.trim() } : {}
      const res = await api.get('/animals', { params })
      setAnimals(res.data)
      setLoading(false)
    } catch (err) {
      alert('Erro ao carregar animais')
      setLoading(false)
    }
  }

  // Fetch animal cares with API data to an especific animal (by ID)
  const fetchAnimalCares = async (animalId) => {
    try {
      const res = await api.get(`/animal-cares/animal/${animalId}`)
      setAnimalCares(res.data)
    } catch (err) {
      console.error(err)
      setAnimalCares([])
    }
  }

  // Load care types with API data
  const loadCareTypes = async () => {
    try {
      const res = await api.get('/care-types')
      setCareTypes(res.data)
    } catch (err) {
      alert('Erro ao carregar tipos de cuidado')
    }
  }

  // Reload animals list on filter change
  useEffect(() => {
    fetchAnimals()
  }, [search])

  // Opens animals care modal by animal ID
  const openAnimalCares = (animal) => {
    setSelectedAnimal(animal)
    fetchAnimalCares(animal.id)
  }

  // Closes animals care modal
  const closeAnimalModal = () => {
    setSelectedAnimal(null)
    setAnimalCares([])
  }

  // Opens modal to a new care
  const openNewCare = () => {
    setIsEditCare(false)
    setEditingCareId(null)
    setCareForm({
      careTypeId: '',
      realizationDate: new Date().toISOString().split('T')[0],
      observations: ''
    })
    loadCareTypes()
    setShowCareModal(true)
  }

  // Opens modal to edit an existing care type
  const openEditCare = (care) => {
    setIsEditCare(true)
    setEditingCareId(care.id)
    setCareForm({
      careTypeId: care.careType.id.toString(),
      realizationDate: care.realizationDate,
      observations: care.observations || ''
    })
    loadCareTypes()
    setShowCareModal(true)
  }

  // Deletes a animal care
  const deleteCare = async (careId, careName) => {
    if (!window.confirm(`Excluir o cuidado "${careName}"?`)) return
    try {
      await api.delete(`/animal-cares/${careId}`)
      alert('Cuidado excluído!')
      fetchAnimalCares(selectedAnimal.id)
    } catch (err) {
      alert('Erro ao excluir')
    }
  }

  // Submit creation/edit care form with validations
  const handleCareSubmit = async (e) => {
    e.preventDefault()
    if (!careForm.careTypeId) {
      alert('Selecione o tipo de cuidado!')
      return
    }

    const today = new Date().toISOString().split('T')[0]
    if (careForm.realizationDate > today) {
      alert('Data não pode ser no futuro!')
      return
    }

    // Defines object sent to API
    try {
      const payload = {
        animalId: selectedAnimal.id,
        careTypeId: parseInt(careForm.careTypeId),
        realizationDate: careForm.realizationDate,
        observations: careForm.observations.trim() || null
      }

      if (isEditCare) {
        await api.put(`/animal-cares/${editingCareId}`, payload)
        alert('Cuidado atualizado!')
      } else {
        await api.post('/animal-cares', payload)
        alert('Cuidado registrado!')
      }

      setShowCareModal(false)
      fetchAnimalCares(selectedAnimal.id)
    } catch (err) {
      alert('Erro ao salvar cuidado')
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
            placeholder="Pesquisar animal..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
        </div>

        <table className="animal-table">
          <thead>
            <tr>
              <th>Nome</th>
              <th>Espécie</th>
              <th>Habitat</th>
              <th>País de Origem</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr><td colSpan="5" style={{textAlign:'center', padding:'60px'}}>Carregando animais...</td></tr>
            ) : animals.length === 0 ? (
              <tr><td colSpan="5" style={{textAlign:'center', padding:'60px', color:'#888'}}>Nenhum animal cadastrado</td></tr>
            ) : (
              animals.map(animal => (
                <tr key={animal.id}>
                  <td><strong>{animal.name}</strong></td>
                  <td>{animal.species?.name || '—'}</td>
                  <td>{animal.habitat?.name || '—'}</td>
                  <td>{animal.country?.name || 'Desconhecido'}</td>
                  <td>
                    <button 
                      className="edit-button"
                      onClick={() => openAnimalCares(animal)}
                      style={{ background: '#2563eb', padding: '8px 16px' }}
                    >
                      Visualizar Cuidados
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </main>

      {selectedAnimal && (
        <div className="modal-overlay" onClick={closeAnimalModal}>
          <div 
            className="modal-content large-cares-modal" 
            onClick={e => e.stopPropagation()}
            style={{
              width: '90%',
              maxWidth: '1200px',
              maxHeight: '90vh',
              overflow: 'hidden',
              display: 'flex',
              flexDirection: 'column'
            }}
          >
            <h2 className="modal-title" style={{ margin: '0 0 20px 0', fontSize: '1.8rem' }}>
              Cuidados Realizados - {selectedAnimal.name}
            </h2>

            <div style={{ marginBottom: '20px' }}>
              <button className="new-registration" onClick={openNewCare}>
                Novo Cuidado
              </button>
            </div>

            <div style={{ flex: 1, overflow: 'auto', marginBottom: '20px' }}>
              <table className="animal-table" style={{ width: '100%' }}>
                <thead style={{ position: 'sticky', top: 0, background: '#1a1a1a', zIndex: 1 }}>
                  <tr>
                    <th style={{ width: '25%' }}>Tipo de Cuidado</th>
                    <th style={{ width: '12%' }}>Frequência</th>
                    <th style={{ width: '12%' }}>Data</th>
                    <th style={{ width: '35%' }}>Observações</th>
                    <th style={{ width: '16%' }}>Ações</th>
                  </tr>
                </thead>
                <tbody>
                  {animalCares.length === 0 ? (
                    <tr>
                      <td colSpan="5" style={{textAlign:'center', padding:'60px', color:'#888'}}>
                        Nenhum cuidado registrado para este animal
                      </td>
                    </tr>
                  ) : (
                    animalCares.map(care => (
                      <tr key={care.id}>
                        <td><strong>{care.careType?.name}</strong></td>
                        <td>{care.careType?.frequency} dias</td>
                        <td>{care.realizationDate}</td>
                        <td style={{ wordBreak: 'break-word', whiteSpace: 'normal' }}>
                          {care.observations || '—'}
                        </td>
                        <td>
                          <button className="edit-button" onClick={() => openEditCare(care)}>
                            Editar
                          </button>
                          <button className="delete-button" onClick={() => deleteCare(care.id, care.careType?.name)}>
                            Excluir
                          </button>
                        </td>
                      </tr>
                    ))
                  )}
                </tbody>
              </table>
            </div>

            <div style={{ textAlign: 'center' }}>
              <button className="cancel-button" onClick={closeAnimalModal}>
                Fechar
              </button>
            </div>
          </div>
        </div>
      )}

      {showCareModal && selectedAnimal && (
        <div className="modal-overlay" onClick={() => setShowCareModal(false)}>
          <div className="modal-content" onClick={e => e.stopPropagation()}>
            <h2 className="modal-title">
              {isEditCare ? 'Editar' : 'Novo'} Cuidado - {selectedAnimal.name}
            </h2>
            <form onSubmit={handleCareSubmit} className="data-container">

              <div className="data-item">
                <span className="data-label">Tipo de Cuidado:</span>
                <select
                  className="data-input"
                  required
                  value={careForm.careTypeId}
                  onChange={e => setCareForm({...careForm, careTypeId: e.target.value})}
                >
                  <option value="">Selecione...</option>
                  {careTypes.map(ct => (
                    <option key={ct.id} value={ct.id}>
                      {ct.name} (a cada {ct.frequency} dias)
                    </option>
                  ))}
                </select>
              </div>

              <div className="data-item">
                <span className="data-label">Data de Realização:</span>
                <input
                  type="date"
                  className="data-input"
                  required
                  value={careForm.realizationDate}
                  onChange={e => setCareForm({...careForm, realizationDate: e.target.value})}
                  max={new Date().toISOString().split('T')[0]}
                />
              </div>

              <div className="data-item">
                <span className="data-label">Observações:</span>
                <textarea
                  className="data-input"
                  rows="5"
                  value={careForm.observations}
                  onChange={e => setCareForm({...careForm, observations: e.target.value})}
                />
              </div>

              <div className="button-group">
                <button type="submit" className="save-button">
                  {isEditCare ? 'Atualizar' : 'Registrar'}
                </button>
                <button type="button" className="cancel-button" onClick={() => setShowCareModal(false)}>
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

export default Cares