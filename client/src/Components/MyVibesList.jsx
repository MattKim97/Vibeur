import React, { useState, useEffect, useRef } from 'react'
import VibeCard from './VibeCard'
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';


gsap.registerPlugin(ScrollTrigger);


const MyVibesList = ({loggedUser}) => {

    const myListRef = useRef(null);


        const [vibes, setVibes] = useState([])
        const [loaded, setLoaded] = useState(false)

        useEffect(() => {
            if (myListRef.current && loaded) {
                gsap.fromTo(myListRef.current, 
                    { opacity: 0 }, 
                    { opacity: 1, duration: 2, ease: "power2.inOut" }
                );
            }
        }, [loaded]);

    
        useEffect(() => {
            fetch(`http://localhost:8080/api/user/${loggedUser.userId}/myVibes`, {
            })
            .then(res => res.json())
            .then(fetchedVibes => {
                setLoaded(true);
                setVibes(fetchedVibes);
            })
            .catch(error => console.error('Error fetching vibes:', error));
        }, []);

        if (vibes.length === 0) {
            if (loaded) {
                return (
                    <div className='container d-flex flex-column align-items-center justify-content-center text-white'>
                        <h1 className='spacing_landing_top display-1'>You have no vibes uploaded!</h1>
                    </div>
                )
            } else {
                return (
                    null
                    // this could be a loading screen or a spinnner placeholder instead
                )
            }
        }

        return (
            <div
              className="d-flex flex-wrap justify-content-center flex-row"
              ref={myListRef}
            >
              {vibes.map((vibe) => (
                <div className="col-12 col-md-4" key={vibe.title}>
                  <VibeCard vibe={vibe} />
                </div>
              ))}
            </div>
          );
        };

export default MyVibesList
