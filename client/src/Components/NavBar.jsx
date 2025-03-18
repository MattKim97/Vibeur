import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import vibeurLogo from '../images/Vibeur.png';
import anonymous from '../images/vector-flat-illustration-grayscale-avatar-600nw-2281862025.webp';


const NavBar = ({loggedUser}) => {
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  const closeDropdown = () => {
    setDropdownOpen(false);
  };

  console.log(loggedUser);

  return (
    <nav className="navbar sticky-top navbar-expand-lg navbar-light bg-light">
      <div className="d-flex flex-row w-100">
        <Link className="navbar-brand" to="/">
          <img src={vibeurLogo} alt="Vibeur Logo" className="image_navbar rounded-pill" />
        </Link>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <Link className="nav-link active" aria-current="page" to="/">Home</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/login">TODO</Link>
            </li>
          </ul>
          <ul className="navbar-nav ms-auto mb-2 mb-lg-0">
            <li className="nav-item dropdown">
              <Link
                className="nav-link"
                to="#"
                onClick={toggleDropdown}
              >
                <img src={loggedUser.userImageUrl != "" ? loggedUser.userImageUrl : anonymous} alt="anonymous" className="image_navbar" />
              </Link>
              {dropdownOpen && (
                <ul className="dropdown-menu show" style={{ position: 'absolute', top: '100%', right: '0', zIndex: 1050 }} aria-labelledby="navbarDropdown">
                  <li><Link className="dropdown-item" to="/login" onClick={closeDropdown}>SignUp / Login</Link></li>
                  <li><Link className="dropdown-item" to="#" onClick={closeDropdown}>Another action</Link></li>
                  <li><hr className="dropdown-divider" /></li>
                  <li><Link className="dropdown-item" to="#" onClick={closeDropdown}>Something else here</Link></li>
                </ul>
              )}
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
