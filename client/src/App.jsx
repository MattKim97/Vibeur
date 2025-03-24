import { useState } from 'react'
import './App.css'
import Landing from './Components/Landing'
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import NavBar from './Components/NavBar';
import LoginForm from './Components/LoginForm';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import EditProfilePic from './Components/EditProfilePic';
import VibeCard from './Components/VibeCard';
import VibesList from './Components/VibesList';
import MyVibesList from './Components/MyVibesList';
import Vibe from './Components/Vibe';
import EditVibeForm from './Components/EditVibeForm';
import CreateVibeForm from './Components/CreateVibeForm';
import NotFound from './Components/NotFound';

function App() {


  const [loggedUser,setLoggedUser] = useState(null)
	const [loaded, setLoaded] = useState(false)

  useEffect(() => {
    const user = localStorage.getItem("loggedUser");
    if (user) {
      setLoggedUser(JSON.parse(user)); // Parse and set the user if found in localStorage
    }
    setLoaded(true);
  }, []); // Only run on mount
  // Save loggedUser in localStorage whenever it changes
  useEffect(() => {
    if (loggedUser) {
      localStorage.setItem("loggedUser", JSON.stringify(loggedUser));
    } else {
      localStorage.removeItem("loggedUser");
    }
  }, [loggedUser]);

  // If loading, return nothing to avoid UI flicker
  if (!loaded) {
    return null;
  }

  console.log(loggedUser)


  return (
    <Router>
      <NavBar loggedUser={loggedUser} setLoggedUser={setLoggedUser}/>
      <Routes>
        <Route path="/" element={<Landing />} />
        <Route path="/editProfile" element={  loggedUser === null ? 
          <Navigate to="/NotFound" /> :
          <EditProfilePic loggedUser={loggedUser} setLoggedUser={setLoggedUser}/> 
          } />
        <Route path="/login" element={<LoginForm setLoggedUser={setLoggedUser}/>} />
        <Route path="/vibes" element={<VibesList/> } />
        <Route path="/myVibes" element={
          loggedUser === null ? 
          <Navigate to="/NotFound" /> :
          <MyVibesList loggedUser={loggedUser}/> } />
        <Route path="/vibe/:vibeId" element={<Vibe loggedUser={loggedUser}/>} />
        <Route path='/vibe/:vibeId/edit' element={loggedUser === null ? 
          <Navigate to="/NotFound" /> :
          <EditVibeForm loggedUser={loggedUser}/>}  />
        <Route path='/vibe/create' element={loggedUser === null ? 
          <Navigate to="/NotFound" /> :
          <CreateVibeForm loggedUser={loggedUser}/>}  />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Router>
  )
}

export default App
