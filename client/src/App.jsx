import { useState } from 'react'
import './App.css'
import Landing from './Components/Landing'
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import NavBar from './Components/NavBar';
import LoginForm from './Components/LoginForm';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function App() {


  const [loggedUser,setLoggedUser] = useState(null)
	const [loaded, setLoaded] = useState(false)
  
  console.log(loggedUser)

	useEffect(() => {
		if(localStorage.getItem("loggedInUser")){
			setLoggedUser(JSON.parse(localStorage.getItem("loggedInUser")))
		}
		setLoaded(true)
	},[])

	if(!loaded){
		return null;
	}

  return (
    <Router>
      <NavBar loggedUser={loggedUser}/>
      <Routes>
        <Route path="/" element={<Landing />} />
        <Route path="/login" element={<LoginForm setLoggedUser={setLoggedUser}/>} />
      </Routes>
    </Router>
  )
}

export default App
