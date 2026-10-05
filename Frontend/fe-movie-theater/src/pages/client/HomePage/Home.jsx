import React from "react";
import Header from "../../../layouts/Header/Header";
import Banner from "./Banner";
import MovieList from "../movielist/MovieList";
import Footer from "../../../layouts/Footer/Footer";

function Home() {
  return (
    <>
      <Header />

      <Banner />

      {/* Movie List */}
      <MovieList />

      {/* Banner Quảng Cáo */}
      {/* <div className="mx-20 mt-1 mb-[-12px]">
        <img src="" alt="Khuyen Mai" className="w-full object-cover" />
      </div> */}

      <Footer />
    </>
  );
}

export default Home;
