import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import api from '../services/api'
import '../styles/animals.css'

function CareTypes() {
  // Care types list by API
  const [careTypes, setCareTypes] = useState([])
  // Loading state
  const [loading, setLoading] = useState(true)
  // Search field state
  const [search, setSearch] = useState('')

  // Modal control creation/edit
  const [showModal, setShowModal] = useState(false)
  const [isEditMode, setIsEditMode] = useState(false)
  const [editingId, setEditingId] = useState(null)

  // Care type form data
  const [formData, setFormData] = useState({
    name: '',
    frequency: ''
  })

  // Fetch care types with API data with optional filters
  const fetchCareTypes = async () => {
    try {
      const params = {}
      if (search.trim()) params.name = search.trim()
      const res = await api.get('/care-types', { params })
      setCareTypes(res.data)
      setLoading(false)
    } catch (err) {
      console.error('Error loading care types:', err)
      alert('Erro ao carregar tipos de cuidado')
      setLoading(false)
    }
  }

  // Reload care types list on filter change
  useEffect(() => {
    fetchCareTypes()
  }, [search])

  // Opens modal in creation mode
  const openCreateModal = () => {
    setIsEditMode(false)
    setEditingId(null)
    setFormData({ name: '', frequency: '' })
    setShowModal(true)
  }

  // Opens modal in editing mode
  const openEditModal = (careType) => {
    setIsEditMode(true)
    setEditingId(careType.id)
    setFormData({
      name: careType.name || '',
      frequency: careType.frequency?.toString() || ''
    })
    setShowModal(true)
  }

  // Deletes an existing care type
  const handleDelete = async (id, name) => {
    if (!window.confirm(`Tem certeza que deseja excluir o tipo de cuidado "${name}"?`)) return

    try {
      await api.delete(`/care-types/${id}`)
      alert('Tipo de cuidado excluído com sucesso!')
      fetchCareTypes()
    } catch (err) {
      alert('Erro ao excluir tipo de cuidado')
    }
  }

  // Submit creation/edit care type form with validations
  const handleSubmit = async (e) => {
    e.preventDefault()

    if (!formData.name.trim()) {
      alert('O nome do tipo de cuidado é obrigatório!')
      return
    }
    if (!formData.frequency || formData.frequency <= 0) {
      alert('A frequência deve ser maior que zero!')
      return
    }

    try {
      // Defines object sent to API
      const payload = {
        name: formData.name.trim(),
        frequency: parseInt(formData.frequency)
      }

      if (isEditMode) {
        await api.put(`/care-types/${editingId}`, payload)
        alert('Tipo de cuidado atualizado com sucesso!')
      } else {
        await api.post('/care-types', payload)
        alert('Tipo de cuidado cadastrado com sucesso!')
      }

      setShowModal(false)
      fetchCareTypes()
    } catch (err) {
      console.error(err.response?.data || err)
      alert(isEditMode ? 'Erro ao atualizar' : 'Erro ao cadastrar')
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
            placeholder="Pesquisar tipo de cuidado..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />

          <button className="new-registration" onClick={openCreateModal}>
            Novo Tipo de Cuidado
          </button>
        </div>

        <table className="animal-table">
          <thead>
            <tr>
              <th>Tipo de Cuidado</th>
              <th>Frequência (dias)</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr><td colSpan="3" style={{textAlign:'center', padding:'60px'}}>Carregando...</td></tr>
            ) : careTypes.length === 0 ? (
              <tr><td colSpan="3" style={{textAlign:'center', padding:'60px', color:'#888'}}>
                Nenhum tipo de cuidado encontrado
              </td></tr>
            ) : (
              careTypes.map(ct => (
                <tr key={ct.id}>
                  <td><strong>{ct.name}</strong></td>
                  <td>{ct.frequency} dia{ct.frequency > 1 ? 's' : ''}</td>
                  <td>
                    <button className="edit-button" onClick={() => openEditModal(ct)}>
                      Editar
                    </button>
                    <button className="delete-button" onClick={() => handleDelete(ct.id, ct.name)}>
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
              {isEditMode ? 'Editar Tipo de Cuidado' : 'Novo Tipo de Cuidado'}
            </h2>
            <form onSubmit={handleSubmit} className="data-container">

              <div className="data-item">
                <span className="data-label">Nome do Tipo de Cuidado:</span>
                <input
                  type="text"
                  className="data-input"
                  required
                  value={formData.name}
                  onChange={e => setFormData({...formData, name: e.target.value})}
                />
              </div>

              <div className="data-item">
                <span className="data-label">Frequência (em dias):</span>
                <input
                  type="number"
                  className="data-input"
                  min="1"
                  required
                  value={formData.frequency}
                  onChange={e => setFormData({...formData, frequency: e.target.value})}
                />
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

export default CareTypes