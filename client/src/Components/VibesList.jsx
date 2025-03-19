import React from "react";
import { useState, useEffect, useRef } from "react";
import VibeCard from "./VibeCard";
import { gsap } from "gsap";
import { ScrollTrigger } from "gsap/ScrollTrigger";

gsap.registerPlugin(ScrollTrigger);

const VibesList = () => {
  const listRef = useRef(null);

  const [vibes, setVibes] = useState([]);
  const [loaded, setLoaded] = useState(false);

   useEffect(() => {
            if (listRef.current && loaded) {
                gsap.fromTo(listRef.current, 
                    { opacity: 0 }, 
                    { opacity: 1, duration: 2, ease: "power2.inOut" }
                );
            }
        }, [loaded]);

  useEffect(() => {
    fetch("http://localhost:8080/api/vibe").then((res) =>
      res.json().then((fetchedVibes) => {
        setLoaded(true);
        setVibes(fetchedVibes);
      })
    );
  }, []);

 if (vibes.length === 0) {
     if (loaded) {
       return (
         <div
           className="container d-flex flex-column align-items-center justify-content-center text-white"
         >
           <h1 className="spacing_landing_top display-1">
             There are no vibes!
           </h1>
         </div>
       );
     } else {
       return null;
     }
   }

  return (
    <div
      className="d-flex flex-wrap justify-content-center flex-row"
      ref={listRef}
    >
      {vibes.map((vibe) => (
        <div className="col-12 col-md-4" key={vibe.title}>
          <VibeCard vibe={vibe} />
        </div>
      ))}
    </div>
  );
};

export default VibesList;
