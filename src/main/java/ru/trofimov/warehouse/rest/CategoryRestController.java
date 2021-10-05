package ru.trofimov.warehouse.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import ru.trofimov.warehouse.model.Category;
import ru.trofimov.warehouse.model.Info;

import javax.servlet.http.HttpServletRequest;

public interface CategoryRestController {

    @Operation(summary = "Get all categories with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found categories",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Info.class))}),
            @ApiResponse(responseCode = "404", description = "Did not find any categories",
                    content = @Content)})
    ResponseEntity<Info<Category>> getAllCategory(HttpServletRequest request, long offset, long limit);

    @Operation(summary = "Get a category by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the category",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Category.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content)})
    ResponseEntity<Category> getCategory(long id);

    @Operation(summary = "Save new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category saved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Category.class))}),
            @ApiResponse(responseCode = "422", description = "Invalid request body",
                    content = @Content)})
    ResponseEntity<Category> saveCategory(HttpServletRequest request, Category category);

    @Operation(summary = "Update a category by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Category.class))}),
            @ApiResponse(responseCode = "422", description = "Invalid request body",
                    content = @Content)})
    ResponseEntity<Category> updateCategory(long id, Category category);

    @Operation(summary = "Remove a category by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content)})
    ResponseEntity<Category> deleteCategory(long id);
}
