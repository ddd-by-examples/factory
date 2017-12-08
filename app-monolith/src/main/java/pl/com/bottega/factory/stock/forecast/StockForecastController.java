package pl.com.bottega.factory.stock.forecast;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/stock/forecasts")
@AllArgsConstructor
class StockForecastController {

    private final StockForecastQuery query;

    @RequestMapping(value = "/{refNo}", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    ResponseEntity<StockForecast> get(@PathVariable("refNo") String refNo) {
        return ResponseEntity.ok(query.get(refNo));
    }
}
