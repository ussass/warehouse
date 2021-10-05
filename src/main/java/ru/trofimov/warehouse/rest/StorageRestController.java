package ru.trofimov.warehouse.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import ru.trofimov.warehouse.model.Info;
import ru.trofimov.warehouse.model.Storage;

import javax.servlet.http.HttpServletRequest;

public interface StorageRestController {

    @Operation(summary = "Get all storages with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found storages",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Info.class))}),
            @ApiResponse(responseCode = "404", description = "Did not find any storages",
                    content = @Content)})
    ResponseEntity<Info<Storage>> getAllStorage(HttpServletRequest request, long offset,
                                                long limit, long categoryId, long goodsId);

    @Operation(summary = "Get a storage by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the storage",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Storage.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Storage not found",
                    content = @Content)})
    ResponseEntity<Storage> getStorage(long id);

    @Operation(summary = "Save new storage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Storage saved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Storage.class))}),
            @ApiResponse(responseCode = "422", description = "Invalid request body",
                    content = @Content)})
    ResponseEntity<Storage> saveStorage(HttpServletRequest request, Storage storage);

    @Operation(summary = "Update a storage by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Storage updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Storage.class))}),
            @ApiResponse(responseCode = "422", description = "Invalid request body",
                    content = @Content)})
    ResponseEntity<Storage> updateStorage(long id, Storage storage);

    @Operation(summary = "Remove a storage by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Storage deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Storage not found",
                    content = @Content)})
    ResponseEntity<Storage> deleteStorage(long id);
}
