import axios from 'axios'

// Centralized axios configuration
const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000, 
  headers: {
    'Content-Type': 'application/json',
  },
})

// Interceptor to show errors
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      // Backend returned error
      console.error('API error:', error.response.data)
      alert(`Error: ${error.response.data.message || 'Something gone wrong!'}`)
    } else if (error.request) {
      // No response
      console.error('Backend not responding:', error.request)
      alert('Verify the backend!')
    } else {
      console.error('Erro:', error.message)
      alert('Unexpected error!')
    }
    return Promise.reject(error)
  }
)

export default api