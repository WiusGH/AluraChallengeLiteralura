import React from "react";
import { Routes, Route } from "react-router-dom";
import HomePage from "./pages/Home";

const App: React.FC = () => {
  return (
    <Routes>
      {/* Define your routes here */}
      <Route path="/" element={<HomePage />} />
    </Routes>
  );
};

export default App;
