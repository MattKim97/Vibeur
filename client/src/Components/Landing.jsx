import React, { useEffect, useRef } from "react";
import { gsap } from "gsap";
import { ScrollTrigger } from "gsap/ScrollTrigger";
import { Link } from "react-router-dom";

gsap.registerPlugin(ScrollTrigger);

const Landing = ({loggedUser}) => {
  const headingRef = useRef(null);
  const paragraphRef = useRef(null);
  const linksRef = useRef(null);
    const loginRef = useRef(null);

  useEffect(() => {
    const fadeInAnimation = (element, delay = 0) => {
      gsap.fromTo(
        element,
        { opacity: 0, y: 50 },
        {
          opacity: 1,
          y: 0,
          duration: 2,
          delay,
          ease: "power2.out",
          scrollTrigger: {
            trigger: element,
            start: "top 80%",
            end: "top 30%",
            toggleActions: "play none none none",
          },
        }
      );
    };

    fadeInAnimation(headingRef.current);
    fadeInAnimation(paragraphRef.current, 0.3);
    fadeInAnimation(loginRef.current);
    fadeInAnimation(linksRef.current, 0.3);
  }, []);

  return (
    <div className="container d-flex flex-column align-items-center justify-content-center text-white">
      <h1 ref={headingRef} className="spacing_landing_top display-1">
        Welcome to <span className="gradient_text">Vibeur</span>
      </h1>

      <div
        className="display-6 width_landing spacing_landing lead gap-1 d-flex flex-row align-items-center justify-content-center"
        ref={paragraphRef}
      >
        <p className="margin_right_landing">
          <span className="gradient_text">Vibeur</span> is a platform for
          sharing your feelings through visual storytelling and vibe-based
          music. It’s a space where users can express emotions, spark
          creativity, and connect with others in the Vibeur community.
        </p>
        <div className="container">
          <div className="ring"></div>
          <div className="ring"></div>
          <div className="ring"></div>
        </div>
      </div>

      <div
        className="display-2 width_landing spacing_landing lead gap-1 d-flex flex-column align-items-center justify-content-center"
        ref={loginRef}

      >
        <p className="margin_right_landing">
          Ready to explore <span className="gradient_text">Vibeur</span> ?
        </p>
        {loggedUser ? <Link to="/vibes" className="btn btn-outline-light">Explore what <span className="gradient_text">Vibeur</span> has to offer!</Link> :         <Link to="/login" className="btn btn-outline-light">Log in or Sign up here to start your <span className="gradient_text">Vibeur</span> Journey!</Link>
        }
      </div>

      <div
        ref={linksRef}
        className="display-6 d-flex flex-column align-items-center justify-content-center spacing_landing spacing_landing_bottom"
      >
        <a
          target="_ "
          href="https://www.linkedin.com/in/matthew-kim-9ba86a15a/"
        >
          <i className="fa-brands fa-linkedin"></i> Matthew Kim
        </a>
        <a target="_ " href="https://github.com/MattKim97/Vibeur/">
          <i className="fa-brands fa-github"></i> Repository
        </a>
      </div>
    </div>
  );
};

export default Landing;
