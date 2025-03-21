import React, { useRef, useEffect, useState } from "react";
import "../MusicPlayer.css";

const MusicPlayer = ({ audioUrl }) => {
  const canvasRef = useRef(null);
  const audioRef = useRef(null);
  const [audioContext, setAudioContext] = useState(null);
  const [isPlaying, setIsPlaying] = useState(false);

  useEffect(() => {
    if (!audioUrl) return;
  
    const audio = new Audio(audioUrl);
    audioRef.current = audio;
    audio.crossOrigin = "anonymous";
  
    const context = new (window.AudioContext || window.webkitAudioContext)();
    setAudioContext(context);
  
    const analyser = context.createAnalyser();
    analyser.fftSize = 256;
    const source = context.createMediaElementSource(audio);
    source.connect(analyser);
    analyser.connect(context.destination);
  
    const bufferLength = analyser.frequencyBinCount;
    const dataArray = new Uint8Array(bufferLength);
  
    const canvas = canvasRef.current;
    const ctx = canvas.getContext("2d");
  
    // Ensure canvas resolution matches CSS size
    canvas.width = 350; // Set the internal resolution
    canvas.height = 350;
  
    const draw = () => {
      requestAnimationFrame(draw);
      analyser.getByteFrequencyData(dataArray);
  
      ctx.clearRect(0, 0, canvas.width, canvas.height);
  
      const centerX = canvas.width / 2;
      const centerY = canvas.height / 2;
      const radius = 100;
      const barCount = bufferLength;
  
      ctx.beginPath();
      ctx.arc(centerX, centerY, radius, 0, 2 * Math.PI);
      ctx.strokeStyle = "#fff";
      ctx.lineWidth = 2;
      ctx.stroke();
  
      for (let i = 0; i < barCount; i++) {
        const angle = (i / barCount) * Math.PI * 2;
        const barHeight = (dataArray[i] / 255) * 50 + 10;
        const x1 = centerX + Math.cos(angle) * radius;
        const y1 = centerY + Math.sin(angle) * radius;
        const x2 = centerX + Math.cos(angle) * (radius + barHeight);
        const y2 = centerY + Math.sin(angle) * (radius + barHeight);
  
        const gradient = ctx.createLinearGradient(0, 0, canvas.width, canvas.height);
        gradient.addColorStop(0, '#ee7752');
        gradient.addColorStop(0.33, '#e73c7e');
        gradient.addColorStop(0.66, '#23a6d5');
        gradient.addColorStop(1, '#23d5ab');
  
        ctx.beginPath();
        ctx.moveTo(x1, y1);
        ctx.lineTo(x2, y2);
        ctx.strokeStyle = gradient;
        ctx.lineWidth = 2;
        ctx.stroke();
      }
    };
  
    draw();
  
    return () => {
      audio.pause();
      context.close();
    };
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

  return (
    <div className="audioVisualizerContainer">
      <canvas ref={canvasRef} className="audioVisualizer"></canvas>
      <button
        className="playPauseButton"
        onClick={togglePlayPause}
      >
        {isPlaying ? "Pause" : "Play"}
      </button>
    </div>
  );
};

export default MusicPlayer;
