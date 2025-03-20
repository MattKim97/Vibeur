import React from "react";
import { useState, useEffect, useRef } from "react";
import VibeCard from "./VibeCard";
import { gsap } from "gsap";
import { ScrollTrigger } from "gsap/ScrollTrigger";



gsap.registerPlugin(ScrollTrigger);

const VibesList = () => {
  const filteredRef = useRef(null);
  const searchRef = useRef(null);



  const [vibes, setVibes] = useState([]);
  const [showSearch, setShowSearch] = useState(false);
  const [filteredVibes, setFilteredVibes] = useState([]);
  const [loaded, setLoaded] = useState(false);


  useEffect(() => {
    fetch("http://localhost:8080/api/vibe").then((res) =>
      res.json().then((fetchedVibes) => {
        setLoaded(true);
        setVibes(fetchedVibes);
        setFilteredVibes(fetchedVibes);
      })
    );
  }, []);

  useEffect(() => {
    if (showSearch && searchRef.current) {
      gsap.fromTo(
        searchRef.current,
        { opacity: 0, y: -20 }, 
        { opacity: 1, y: 0, duration: 1.5, ease: "power2.inOut" }
      );
    }
  }, [showSearch]);

  useEffect(() => {
    if (filteredRef.current) {
      gsap.fromTo(
        filteredRef.current,
        { opacity: 0 },
        { opacity: 1, duration: 1.5, ease: "power2.inOut" }
      );
    }
  }, [filteredVibes]);
  




if(!vibes){
  return null;
}

console.log(vibes)

// const mostLikedVibes = vibes.sort((a, b) => b.likes.length - a.likes.length);

const mostRecentVibes = vibes.sort((a, b) => new Date(b.dateUploaded) - new Date(a.dateUploaded));
const happyVibes = vibes.filter((vibe) => vibe.mood.moodName === "happy");
const sadVibes = vibes.filter((vibe) => vibe.mood.moodName === "sad");
const angryVibes = vibes.filter((vibe) => vibe.mood.moodName === "angry");
const excitedVibes = vibes.filter((vibe) => vibe.mood.moodName === "excited");
const relaxedVibes = vibes.filter((vibe) => vibe.mood.moodName === "relaxed");



  return (
    <>
    <div className="me-auto ms-auto d-flex flex-row">
      
      <select
          className="form-select text-center select_style_list ms-5 mt-5"
  onChange={(e) => {
    switch (e.target.value) {
      case 'mostRecent':
        setShowSearch(false);
        setFilteredVibes(mostRecentVibes);
        break;
      case "byTitle":
        setShowSearch(true);
        break
      case 'happy':
        setShowSearch(false);
        setFilteredVibes(happyVibes);
        break;
      case 'sad':
        setShowSearch(false);
        setFilteredVibes(sadVibes);
        break;
      case 'angry':
        setShowSearch(false);
        setFilteredVibes(angryVibes);
        break;
      case 'excited':
        setShowSearch(false);
        setFilteredVibes(excitedVibes);
        break;
      case 'relaxed':
        setShowSearch(false);
        setFilteredVibes(relaxedVibes);
        break;
      default:
        break;
    }
  }}
>
  <option value="mostRecent">Most Recent</option>
  <option value="byTitle">By Title</option>
  <option value="happy">Happy</option>
  <option value="sad">Sad</option>
  <option value="angry">Angry</option>
  <option value="excited">Excited</option>
  <option value="relaxed">Relaxed</option>
</select>
{showSearch && (
  <input
    type="text"
    ref={searchRef}
    className="form-control text-center select_style_list ms-2 me-auto mt-5"
    placeholder={"🔍 Search by title"}
    onChange={(e) => {
      const search = e.target.value.toLowerCase();
      const filtered = vibes.filter((vibe) =>
        vibe.title.toLowerCase().includes(search)
      );
      setFilteredVibes(filtered);
    }}
  />
)}
    </div>
    <div
      className="d-flex flex-wrap justify-content-center flex-row"
      ref={filteredRef}
    >
      {filteredVibes.map((vibe) => (
        <div className="col-12 col-md-4" key={vibe.title}>
          <VibeCard vibe={vibe} />
        </div>
      ))}
    </div>
    {filteredVibes.length === 0 && loaded && (
      <div className="text-center mt-5 text-white">
        <h2 className="display-2">No vibes found</h2>
      </div>
    )}
    </>
  );
};

export default VibesList;
