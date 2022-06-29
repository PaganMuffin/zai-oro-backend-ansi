package com.example.ansi.utills;

import com.example.ansi.model.anilist.AniListEntry;
import com.example.ansi.model.anilist.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.minidev.json.JSONObject;

public class AniList {

    static String GraphQLURL = "https://graphql.anilist.co";

    public static String getFirstsRecords(Integer p) throws UnirestException {
        String GraphQLQuery = """
                    query ($id: Int, $page: Int, $perPage: Int) {
                    Page (page: $page, perPage: $perPage) {
                        pageInfo {
                            total
                                    currentPage
                            lastPage
                                    hasNextPage
                            perPage
                        }
                        media (id: $id, type: ANIME) {
                            id
                            idMal
                            title {
                                romaji
                            }
                            coverImage{
                                medium
                            }
                            format
                                    season
                            seasonYear
                                    description
                            episodes
                        }
                    }
                }""";

        JSONObject variables = new JSONObject();
        variables.put("page", p);
        variables.put("perPage", 10);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query", GraphQLQuery);
        jsonObject.put("variables", variables);

        HttpResponse<JsonNode> response = Unirest.post(GraphQLURL)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(jsonObject.toJSONString())
                .asJson();

        //Prettifying
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.getBody().toString());
        return gson.toJson(je);
    }

    public static String searchByTitle(String q, Integer p) throws UnirestException {
        String GraphQLQuery = """
                    query ($id: Int, $page: Int, $perPage: Int, $search: String) {
                    Page (page: $page, perPage: $perPage) {
                        pageInfo {
                            total
                                    currentPage
                            lastPage
                                    hasNextPage
                            perPage
                        }
                        media (id: $id, search: $search, type: ANIME) {
                            id
                            idMal
                            title {
                                romaji
                            }
                            coverImage{
                                medium
                            }
                            format
                                    season
                            seasonYear
                                    description
                            episodes
                        }
                    }
                }""";

        JSONObject variables = new JSONObject();
        variables.put("page", p);
        variables.put("perPage", 10);
        variables.put("search", q);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query", GraphQLQuery);
        jsonObject.put("variables", variables);

        HttpResponse<JsonNode> response = Unirest.post(GraphQLURL)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(jsonObject.toJSONString())
                .asJson();

        //Prettifying
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.getBody().toString());
        return gson.toJson(je);
    }

    //GET by ID
    public static AniListEntry getById(Integer id) throws UnirestException {
        String GraphQLQuery = """
                query ($id: Int) {
                    Media (id: $id) {
                      id
                      idMal
                      title {
                        romaji
                      }
                      coverImage{
                        medium
                      }
                      format
                      season
                      seasonYear
                      description
                      episodes
                    }
                }""";

        JSONObject variables = new JSONObject();
        variables.put("id", id);

        JSONObject body = new JSONObject();
        body.put("query", GraphQLQuery);
        body.put("variables", variables);

        HttpResponse<JsonNode> response = Unirest.post(GraphQLURL)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(body.toJSONString())
                .asJson();

        if (response.getStatus() != 200) {
            return null;
        }
        //response to java class
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response.getBody().toString(), Response.class).getData().getMedia();
    }

}
