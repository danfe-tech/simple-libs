CREATE TABLE IF NOT EXISTS songs(
                song_key character varying(255) NOT NULL,
                filename character varying(255),
                price double precision NOT NULL,
                title character varying(255),
                created date,
                note character varying(255),
                CONSTRAINT songs_pkey PRIMARY KEY (song_key)
                )