import React, { useState } from "react";
import { Link } from "react-router-dom";
import vibeurLogo from "../images/Vibeur.png";
import anonymous from "../images/anonymous.png";
import { useNavigate } from "react-router-dom";

const NavBar = ({ loggedUser, setLoggedUser }) => {
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const navigate = useNavigate();

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  const closeDropdown = () => {
    setDropdownOpen(false);
  };

  const handleLogOut = () => {
    navigate("/login");
    localStorage.removeItem("loggedInUser");
    setLoggedUser(null);
  };


  return (
    <nav className="navbar sticky-top navbar-expand-lg navbar-light d-flex flex-row w-100 align-items-center">
      <div className="d-flex flex-row w-100 align-items-center">
        <Link className="navbar-brand ms-4 gradient_text logo_landing_text" to="/">
          Vibeur
        </Link>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <Link className="nav-link active text-white" aria-current="page" to="/">
                Home
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link text-white" to="/vibes">
                Browse
              </Link>
            </li>
            {loggedUser != null ?   <li className="nav-item">
              <Link className="nav-link text-white" to="/vibe/create">
                Upload a Vibe
              </Link>
            </li> : null}
          </ul>
          <ul className="navbar-nav ms-auto mb-2 mb-lg-0">
            <div className="d-flex flex-row align-items-center">
            {loggedUser != null ? <li className="nav-item text-white">Welcome {loggedUser.username}</li> : null }
            <li className="nav-item dropdown">
              <Link className="nav-link me-4" to="#" onClick={toggleDropdown}>
                <img
                  src={
                    loggedUser != null && loggedUser.userImageUrl != ""
                      ? loggedUser.userImageUrl
                      : anonymous
                  }
                  alt="anonymous"
                  className="image_navbar"
                />
              </Link>
              {dropdownOpen && (
               <ul
               className="dropdown-menu show"
               style={{
                 position: "absolute",
                 top: "90%",
                 right: "10px", // Moves it 10px to the left
                 zIndex: 1050,
               }}
               aria-labelledby="navbarDropdown"
             >
                  {loggedUser == null ? (
                    <li>
                      <Link
                        className="dropdown-item text-center"
                        to="/login"
                        onClick={closeDropdown}
                      >
                        Login
                      </Link>
                    </li>
                  ) : null}
                  {loggedUser != null ? (
                    <li>
                      <Link
                        className="dropdown-item text-center"
                        to="/myVibes"
                        onClick={closeDropdown}
                      >
                        My Vibes
                      </Link>
                      <li>
                        <hr className="dropdown-divider" />
                      </li>
                    </li>
                  ) : null}
                   {loggedUser != null ? (
                    <li>
                      <Link
                        className="dropdown-item text-center"
                        to="/editProfile"
                        onClick={closeDropdown}
                      >
                        Edit Profile Picture
                      </Link>
                      <li>
                        <hr className="dropdown-divider" />
                      </li>
                    </li>
                  ) : null}
                  {loggedUser != null ? (
                    <li>
                      <button className="dropdown-item text-center" onClick={handleLogOut}>
                        Log Out
                      </button>
                    </li>
                  ) : null}
                </ul>
              )}
            </li>
            </div>
            
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
