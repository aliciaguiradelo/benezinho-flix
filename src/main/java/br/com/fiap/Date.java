package br.com.fiap;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Data;

import java.util.ArrayList;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */

@Data
@Entity
@Table(name = "TB_DATE")
public class Date {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_DATE")
    @SequenceGenerator(name = "SQ_DATE", sequenceName = "SQ_DATE", allocationSize = 1, initialValue = 1)
    private Long id;

    @JsonProperty("maximum")
    private String maximum;

    @JsonProperty("minimum")
    private String minimum;
}

@Data
@Entity
@Table(name = "TB_RESULT")
class Result {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_RESULT")
    @SequenceGenerator(name = "SQ_RESULT", sequenceName = "SQ_RESULT", allocationSize = 1, initialValue = 1)
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("adult")
    private boolean adult;

    @JsonProperty("backdrop_path")
    private String backdropPath;


    @ManyToMany
    @JoinTable(name = "TB_RESULT_GENERO",
            joinColumns = {
                    @JoinColumn(name = "RESULT", columnDefinition = "ID_RESULT", foreignKey = @ForeignKey(name = "FK_TB_RESULT_RESULT"))
            }
    )
    @JsonProperty("genre_ids")
    private ArrayList<Integer> genreIds = new ArrayList<>();


    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("popularity")
    private Double popularity;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("title")
    private String title;

    @JsonProperty("video")
    private boolean video;

    @JsonProperty("vote_average")
    private double voteAverage;

    @JsonProperty("vote_count")
    private Integer voteCount;
}

@Data
@Entity
@Table(name = "TB_ROOT")
class Root {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ROOT")
    @SequenceGenerator(name = "SQ_ROOT", sequenceName = "SQ_ROOT", allocationSize = 1, initialValue = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "DATE", columnDefinition = "ID_DATE", foreignKey = @ForeignKey(name = "FK_TB_ROOT_DATE"))
    @JsonProperty("dates")
    private Date date;

    @JsonProperty("page")
    private Integer page;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "TB_ROOT_RESULT",
            joinColumns = {
                    @JoinColumn(name = "ROOT", columnDefinition = "ID_ROOT", foreignKey = @ForeignKey(name = "FK_TB_ROOT_ROOT"))
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "RESULT", columnDefinition = "ID_RESULT", foreignKey = @ForeignKey(name = "FK_TB_ROOT_RESULT"))
            }
    )
    @JsonProperty("results")
    private ArrayList<Result> results = new ArrayList<>();

    @JsonProperty("total_pages")
    private Integer totalPages;

    @JsonProperty("total_results")
    private Integer totalResults;
}

