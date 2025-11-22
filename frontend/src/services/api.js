import axios from 'axios'

// Configuração centralizada do Axios
const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000, 
  headers: {
    'Content-Type': 'application/json',
  },
})

// Interceptor pra mostrar erros
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      // Erro retornado pelo backend
      console.error('Erro na API:', error.response.data)
      alert(`Erro: ${error.response.data.message || 'Algo deu errado'}`)
    } else if (error.request) {
      // Sem resposta
      console.error('Backend não respondeu:', error.request)
      alert('Não foi possível conectar ao servidor. Verifique se o backend está rodando.')
    } else {
      console.error('Erro:', error.message)
      alert('Erro inesperado')
    }
    return Promise.reject(error)
  }
)

export default api