import React, { useRef, useEffect, useState } from "react";
import "../MusicPlayer.css";

const MusicPlayer = ({ audioUrl }) => {
  const canvasRef = useRef(null);
  const audioRef = useRef(null);
  const [audioContext, setAudioContext] = useState(null);
  const [isPlaying, setIsPlaying] = useState(false);

  // references and states to keep track of audio and the canvas which is what the music player is mounted on

  useEffect(() => {
    if (!audioUrl) return;

    //use effect changes depending on the audio url
  
    const audio = new Audio(audioUrl);
    audioRef.current = audio;
    audio.crossOrigin = "anonymous";

    
    const context = new (window.AudioContext || window.webkitAudioContext)();
    setAudioContext(context);

    // create a new audio context 
  
    const analyser = context.createAnalyser();
    analyser.fftSize = 512;    
    const source = context.createMediaElementSource(audio);
    source.connect(analyser);
    analyser.connect(context.destination);

    // create a audio analyser and connect it to the audio source and to our speakers
  
    const bufferLength = analyser.frequencyBinCount;
    const dataArray = new Uint8Array(bufferLength);

    // create a data array to store the frequency data from the audio

  
    const canvas = canvasRef.current;
    const ctx = canvas.getContext("2d");
  
    canvas.width = 350;
    canvas.height = 350;

    // create the canvas and set its dimensions
  
    const draw = () => {
      analyser.getByteFrequencyData(dataArray);

      if (dataArray.every((val) => val === 0)) {
        requestAnimationFrame(draw);
        return;
      }



      ctx.clearRect(0, 0, canvas.width, canvas.height);

      // clear the canvas before drawing the next frame
  
      const centerX = canvas.width / 2;
      const centerY = canvas.height / 2;
      const radius = 100;
      const barCount = bufferLength;
  
      ctx.beginPath();
      ctx.arc(centerX, centerY, radius, 0, 2 * Math.PI);
      ctx.strokeStyle = "#fff";
      ctx.lineWidth = 2;
      ctx.stroke();

      //define and draw the circle in the middle of the visualizer

      
      let minHeight = 10;
      
      const maxVal = Math.max(...dataArray) || 1;
      // set the minimum height of the bars and set the max value to the highest value within the data array

      const getRandom = () => Math.floor(Math.random() * 21) + 25;

      for(let i = 0; i < dataArray.length; i++){
        if(dataArray[i] === 0){
          dataArray[i] = getRandom();
        }
      }
      // fill the data array with random values if the value is 0 to prevent empty spaces in the visualizer

      if(audioRef.current.paused){
        dataArray.fill(0);
      }

      // if the audio is paused revert the data array to before song is played


      requestAnimationFrame(draw);

      


      for (let i = 0; i < barCount; i++) {
        const angle = (i / barCount) * Math.PI * 2;
        let barHeight = Math.max(((dataArray[i] / maxVal) * 50) + minHeight, minHeight);
        const x1 = centerX + Math.cos(angle) * radius;
        const y1 = centerY + Math.sin(angle) * radius;
        const x2 = centerX + Math.cos(angle) * (radius + barHeight);
        const y2 = centerY + Math.sin(angle) * (radius + barHeight);

        // draw the bars in the visualizer
  
        const gradient = ctx.createLinearGradient(0, 0, canvas.width, canvas.height);
        gradient.addColorStop(0, '#ee7752');
        gradient.addColorStop(0.33, '#e73c7e');
        gradient.addColorStop(0.66, '#23a6d5');
        gradient.addColorStop(1, '#23d5ab');

        // create a gradient for the bars
  
        ctx.beginPath();
        ctx.moveTo(x1, y1);
        ctx.lineTo(x2, y2);
        ctx.strokeStyle = gradient;
        ctx.lineWidth = 2;
        ctx.stroke();

        // draw the bars in the visualizer with the gradient
      }
    };
  
    draw();
  
    return () => {
      audio.pause();
      context.close();
    };

    // clean up function to stop the audio and close the audio context
  }, [audioUrl]);
  
  

  const togglePlayPause = () => {
    if (audioContext && audioRef.current) {
      if (isPlaying) {
        audioRef.current.pause();
      } else {
        audioContext.resume();
        audioRef.current.play();
      }
      setIsPlaying(!isPlaying);
    }
  };

  // handles the play and pause of the audio

  return (
    <div className="audioVisualizerContainer">
      <canvas ref={canvasRef} className="audioVisualizer"></canvas>
      <button
        className={isPlaying ? "playPauseButtonTransparent" : "playPauseButton"}
        onClick={togglePlayPause}
      >
        {isPlaying ? "Pause" : "Play"}
      </button>
    </div>
  );
};

export default MusicPlayer;
