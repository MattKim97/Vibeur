import React, { useEffect, useRef } from 'react';
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';

gsap.registerPlugin(ScrollTrigger);

const Landing = () => {
    const headingRef = useRef(null);
    const paragraphRef = useRef(null);
    const linksRef = useRef(null);


    useEffect(() => {

        gsap.set("body", { backgroundColor: '#A6C9A6' });

        gsap.to("body", {
            backgroundColor: '#A6C9A6', // whitesmoke
            scrollTrigger: {
                trigger: headingRef.current,
                start: 'top 70%',
                end: 'bottom 30%',
                scrub: true,
            }
        });

        gsap.to("body", {
            backgroundColor: '#74AA74  ', //
            scrollTrigger: {
                trigger: paragraphRef.current,
                start: 'top 80%',
                end: 'bottom 30%',
                scrub: true,
            }
        });

        gsap.to("body", {
            backgroundColor: '#4E7E4E ', // 
            scrollTrigger: {
                trigger: linksRef.current,
                start: 'top 80%',
                end: 'bottom 30%',
                scrub: true,
            }
        });

        // text animations
        const fadeInAnimation = (element, delay = 0) => {
            gsap.fromTo(
                element,
                { opacity: 0, y: 50 },  
                {
                    opacity: 1,
                    y: 0,
                    duration: 2,
                    delay,
                    ease: 'power2.out',
                    scrollTrigger: {
                        trigger: element,
                        start: 'top 70%',
                        end: 'top 30%',
                        toggleActions: 'play none none none',
                    }
                }
            );
        };

        fadeInAnimation(headingRef.current);
        fadeInAnimation(paragraphRef.current,0.3);
        fadeInAnimation(linksRef.current,0.3);

    }, []);

    return (
        <div className='container d-flex flex-column align-items-center justify-content-center'>
            <h1 ref={headingRef} className="spacing_landing_top display-1">
                Welcome to Vibeur!
            </h1>

            <p
                ref={paragraphRef}
                className='display-6 width_landing spacing_landing lead'
            >
                Vibeur is an app that displays a new way of sharing how your vibes are at the moment! Vibeur offers a unique blend of visual storytelling with vibe-based music integrations. Vibeur allows users to express their emotions and creativity, find inspiration, connections, and comfort within the Vibeur community!
            </p>

            <div
                ref={linksRef}
                className='display-6 d-flex flex-column align-items-center justify-content-center spacing_landing spacing_landing_bottom'
            >
                <a target="_ " href="https://www.linkedin.com/in/matthew-kim-9ba86a15a/">
                    <i className="fa-brands fa-linkedin"></i>  Matthew Kim
                </a>
                <a target="_ " href="https://github.com/MattKim97/Vibeur/">
                    <i className="fa-brands fa-github"></i>  Repository
                </a>
            </div>
        </div>
    );
};

export default Landing;
