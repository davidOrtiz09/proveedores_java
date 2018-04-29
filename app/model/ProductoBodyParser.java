package model;


import akka.util.ByteString;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.F;
import play.libs.streams.Accumulator;
import play.mvc.BodyParser;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.util.concurrent.Executor;

 public class ProductoBodyParser implements BodyParser<Producto> {
    private BodyParser.Json jsonParser;
    private Executor executor;

    @Inject
    public ProductoBodyParser(BodyParser.Json jsonParser, Executor executor) {
        this.jsonParser = jsonParser;
        this.executor = executor;
    }

     @Override
     public Accumulator<ByteString, F.Either<Result, Producto>> apply(Http.RequestHeader request) {
         Accumulator<ByteString, F.Either<Result, JsonNode>> jsonAccumulator = jsonParser.apply(request);
         return jsonAccumulator.map(resultOrJson -> {
             if (resultOrJson.left.isPresent()) {
                 return F.Either.Left(resultOrJson.left.get());
             } else {
                 JsonNode json = resultOrJson.right.get();
                 try {
                     Producto producto = play.libs.Json.fromJson(json, Producto.class);
                     return F.Either.Right(producto);
                 } catch (Exception e) {
                     return F.Either.Left(Results.badRequest(
                             "Unable to read Producto from json: " + e.getMessage()));
                 }
             }
         }, executor);
     }
 }
