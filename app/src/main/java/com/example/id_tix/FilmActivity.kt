package com.example.id_tix

data class FilmList(
    val id: Int,
    val title: String,
    val duration: Int,
    val director: String,
    val genre: String,
    val producers: String,
    val productionCompany: String,
    val casts: String,
    val synopsis: String,
    val poster: Int
)

val filmList = listOf(
    FilmList(
        id = 1,
        title = "Joker",
        duration = 122,
        director = "Todd Phillips",
        genre = "Crime, Drama, Thriller",
        producers = "Todd Phillips, Bradley Cooper, Emma Tillinger Koskoff",
        productionCompany = "Warner Bros. Pictures",
        casts = "Joaquin Phoenix, Robert De Niro, Zazie Beetz",
        synopsis = "In Gotham City, Arthur Fleck, a mentally troubled comedian, is disregarded and mistreated by society. He begins a slow descent into madness and transforms into the criminal mastermind known as the Joker.",
        poster = R.drawable.ic_launcher_poster
    ),
    FilmList(
        id = 2,
        title = "Star Wars",
        duration = 121,
        director = "George Lucas",
        genre = "Action, Adventure, Fantasy",
        producers = "Gary Kurtz, George Lucas",
        productionCompany = "Lucasfilm",
        casts = "Mark Hamill, Harrison Ford, Carrie Fisher",
        synopsis = "Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a Wookiee, and two droids to save the galaxy from the Empire's world-destroying battle station.",
        poster = R.drawable.ic_launcher_poster2
    ),
    FilmList(
        id = 3,
        title = "Blade Runner 2049",
        duration = 164,
        director = "Denis Villeneuve",
        genre = "Sci-Fi, Thriller",
        producers = "Andrew A. Kosove, Broderick Johnson, Bud Yorkin",
        productionCompany = "Warner Bros. Pictures",
        casts = "Ryan Gosling, Harrison Ford, Ana de Armas",
        synopsis = "Young Blade Runner K's discovery of a long-buried secret leads him to track down former Blade Runner Rick Deckard, who's been missing for thirty years.",
        poster = R.drawable.ic_launcher_poster3
    ),
    FilmList(
        id = 4,
        title = "Spiderman Far From Home",
        duration = 129,
        director = "Jon Watts",
        genre = "Action, Adventure, Sci-Fi",
        producers = "Kevin Feige, Amy Pascal",
        productionCompany = "Marvel Studios",
        casts = "Tom Holland, Samuel L. Jackson, Jake Gyllenhaal",
        synopsis = "Peter Parker's European vacation takes an unexpected turn when he reluctantly agrees to help Nick Fury uncover the mystery of elemental creature attacks.",
        poster = R.drawable.ic_launcher_poster4
    ),
    FilmList(
        id = 5,
        title = "Avengers Endgame",
        duration = 181,
        director = "Anthony Russo, Joe Russo",
        genre = "Action, Adventure, Drama",
        producers = "Kevin Feige",
        productionCompany = "Marvel Studios",
        casts = "Robert Downey Jr., Chris Evans, Mark Ruffalo, Scarlett Johansson",
        synopsis = "After the devastating events of Infinity War, the Avengers assemble once more to reverse Thanos' actions and restore balance to the universe.",
        poster = R.drawable.ic_launcher_poster5
    ),
    FilmList(
        id = 6,
        title = "La La Land",
        duration = 128,
        director = "Damien Chazelle",
        genre = "Comedy, Drama, Music",
        producers = "Fred Berger, Jordan Horowitz, Gary Gilbert, Marc Platt",
        productionCompany = "Summit Entertainment",
        casts = "Ryan Gosling, Emma Stone, John Legend",
        synopsis = "While navigating their careers in Los Angeles, a pianist and an actress fall in love while attempting to reconcile their aspirations for the future.",
        poster = R.drawable.ic_launcher_poster6
    ),
    FilmList(
        id = 7,
        title = "The Grand Budapest Hotel",
        duration = 99,
        director = "Wes Anderson",
        genre = "Adventure, Comedy, Crime",
        producers = "Wes Anderson, Scott Rudin, Steven Rales",
        productionCompany = "Fox Searchlight Pictures",
        casts = "Ralph Fiennes, Tony Revolori, Adrien Brody",
        synopsis = "A writer encounters the owner of an aging high-class hotel, who tells him of his early years serving as a lobby boy in the hotel's glorious years.",
        poster = R.drawable.ic_launcher_poster7
    ),
    FilmList(
        id = 8,
        title = "Minecraft Movie",
        duration = 105,
        director = "Jared Hess",
        genre = "Adventure, Family, Fantasy",
        producers = "Roy Lee, Jill Messick, Mary Parent",
        productionCompany = "Warner Bros. Pictures",
        casts = "Jason Momoa, Danielle Brooks",
        synopsis = "A teenage girl and her unlikely group of adventurers must save their blocky Overworld from the destructive Ender Dragon.",
        poster = R.drawable.ic_launcher_poster8
    ),
    FilmList(
        id = 9,
        title = "Interstellar",
        duration = 169,
        director = "Christopher Nolan",
        genre = "Adventure, Drama, Sci-Fi",
        producers = "Emma Thomas, Christopher Nolan, Lynda Obst",
        productionCompany = "Paramount Pictures",
        casts = "Matthew McConaughey, Anne Hathaway, Jessica Chastain",
        synopsis = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival as Earth becomes uninhabitable.",
        poster = R.drawable.ic_launcher_poster9
    )
)

val comingSoonList = listOf(
    FilmList(
        id = 10,
        title = "Bad Genius",
        duration = 96,
        director = "J.C. Lee",
        genre = "Crime, Drama",
        producers = "Patrick Wachsberger, Erik Feig, Jessica Switch, Ashley Stern",
        productionCompany = "Vertical Entertainment",
        casts = "Callina Liang, Benedict Wong, Jabari Banks",
        synopsis = "A group of senior high school students, trying to remove the cheating system of new student admissions at a famous university.",
        poster = R.drawable.ic_launcher_poster12
    ),
    FilmList(
        id = 11,
        title = "Ballerina",
        duration = 124,
        director = "Len Wiseman",
        genre = "Action, Thriller",
        producers = "Basil Iwanyk, Erica Lee, Chad Stahelski",
        productionCompany = "Lionsgate",
        casts = "Ana de Armas, Keanu Reeves, Ian McShane",
        synopsis = "Eve (Ana de Armas) a trained assassin in the tradition of the Roman Ruska organization set out for revenge after her father's death.",
        poster = R.drawable.ic_launcher_poster11
    ),
    FilmList(
        id = 12,
        title = "How To Train Your Dragon",
        duration = 125,
        director = "Dean DeBlois",
        genre = "Fantasy",
        producers = "Dean DeBlois, Marc Platt, Adam Siegel",
        productionCompany = "Universal Pictures",
        casts = "Nico Parker, Gerard Butler",
        synopsis = "When an ancient threat threatens the Vikings on the island of Berk, the friendship between Hiccup (Mason Thames), an innovative Viking, and Toothless, a Night Fury dragon, becomes the key for both species to make a new future together.",
        poster = R.drawable.ic_launcher_poster13
    ),
    FilmList(
        id = 13,
        title = "Mission: Impossible - The Final Reckoning",
        duration = 169,
        director = "Christopher McQuarrie",
        genre = "Action, Thriller",
        producers = "Tom Cruise, Christopher McQuarrie",
        productionCompany = "Paramount Pictures",
        casts = "Tom Cruise, Hayley Atwell, Ving Rhames",
        synopsis = "Continuing the story from the previous film, Mission: Impossible - Dead Reckoning Part One. Ethan Hunt (Tom Cruise) will return to continue the mission to destroy old enemies and new enemies that are more dangerous.",
        poster = R.drawable.ic_launcher_poster10
    ),
    FilmList(
        id = 14,
        title = "Locked",
        duration = 95,
        director = "David Yarovesky",
        genre = "Thriller, Suspense",
        producers = "Sam Raimi and Zainab Azizi",
        productionCompany = "The Avenue Entertainment",
        casts = "Bill Skarsgard, Anthony Hopkins, Gabriella Waish",
        synopsis = "Eddie Barrish (Bill Skarsgard) is a thief who tries to break into a luxury SUV. What he doesn't know is that he has fallen into a dangerous psychological game made by William (Anthony Hopkins) the owner of the car.",
        poster = R.drawable.ic_launcher_poster14
    )
)

/*
data class FilmList(
    val title: String,
    val poster: Int
)

val filmList = listOf(
    FilmList("Joker", R.drawable.ic_launcher_poster),
    FilmList("Star Wars", R.drawable.ic_launcher_poster2),
    FilmList("Blade Runner 2049", R.drawable.ic_launcher_poster3),
    FilmList("Spiderman Far From Home", R.drawable.ic_launcher_poster4),
    FilmList("Avengers Endgame", R.drawable.ic_launcher_poster5),
    FilmList("La La Land", R.drawable.ic_launcher_poster6),
    FilmList("The Grand Budapest Hotel", R.drawable.ic_launcher_poster7),
    FilmList("Minecraft Movie", R.drawable.ic_launcher_poster8),
    FilmList("Interstellar", R.drawable.ic_launcher_poster9),
)

val comingSoonList = listOf(
    FilmList("Bad Genius", R.drawable.ic_launcher_poster12),
    FilmList("Ballerina", R.drawable.ic_launcher_poster11),
    FilmList("How to Train Your Dragon", R.drawable.ic_launcher_poster13),
    FilmList("Mission: Impossible - The Final Reckoning", R.drawable.ic_launcher_poster10),
    FilmList("Locked", R.drawable.ic_launcher_poster14),
)
*/