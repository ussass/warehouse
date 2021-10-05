package ru.trofimov.warehouse.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import ru.trofimov.warehouse.model.Goods;
import ru.trofimov.warehouse.model.Info;

import javax.servlet.http.HttpServletRequest;

public interface GoodsRestController {

    @Operation(summary = "Get all goods with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found goods",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Info.class))}),
            @ApiResponse(responseCode = "404", description = "Did not find any goods",
                    content = @Content)})
    ResponseEntity<Info<Goods>> getAllGoods(HttpServletRequest request, long offset, long limit, long categoryId);

    @Operation(summary = "Get a good by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the good",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Goods.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Good not found",
                    content = @Content)})
    ResponseEntity<Goods> getGoods(long id);

    @Operation(summary = "Save new good")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Good saved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Goods.class))}),
            @ApiResponse(responseCode = "422", description = "Invalid request body",
                    content = @Content)})
    ResponseEntity<Goods> saveGoods(HttpServletRequest request, Goods goods);

    @Operation(summary = "Update a good by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Good updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Goods.class))}),
            @ApiResponse(responseCode = "422", description = "Invalid request body",
                    content = @Content)})
    ResponseEntity<Goods> updateGoods(long id, Goods goods);

    @Operation(summary = "remove a good by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Good deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Good not found",
                    content = @Content)})
    ResponseEntity<Goods> deleteGoods(long id);
}
