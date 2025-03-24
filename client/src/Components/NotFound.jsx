import React, { useEffect } from 'react';
import '../NotFound.css';
import { Link } from 'react-router-dom';

const NotFound = () => {
  useEffect(() => {
    const element = document.querySelector('.cont_principal');
    element.classList.remove('cont_error_active');
    void element.offsetWidth; 
    element.classList.add('cont_error_active');
  }, []);

  return (
    <div className="cont_principal">
      <div className="cont_error">
        <h1 className='gradient_text'>Oops</h1>
        <p className='gradient_text'>The Page you're looking for isn't here.</p>
        <Link to="/" className="gradient_text">Go back home</Link>
      </div>
      <div className="cont_aura_1"></div>
      <div className="cont_aura_2"></div>
    </div>
  );
};

export default NotFound;
