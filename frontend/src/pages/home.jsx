import { Link } from 'react-router-dom'
import '../styles/Home.css'

function Home() {
  return (
    <>
      <header>
        <Link to="/"><h1>Gerenciamento do Zoológico</h1></Link>
      </header>

      <main>
        <section className="menu-items">
          <div className="menu-item">
            <Link to="/animals">Animais</Link>
          </div>

          <div className="menu-item">
            <Link to="/care-types">Cuidados</Link>
          </div>

          <div className="menu-item">
            <Link to="/cares">Histórico</Link>
          </div>
        </section>
      </main>
    </>
  )
}

export default Home