#' /* setup
library(FactoryAnalytics)
productRefNo <- "3009013"
#' */

#' ---
#' title: "Initial demand forecasting expertise
#' author: "Michał Michaluk"
#' output: pdf_document
#' ---

#' # Initial demand forecasting expertise of new product release
{{productRefNo}}

#' ## Method exmplanation
#' Demand forecasting for new product will be calculated based on relationship to existing products of the same family namly *3009002* and *3009004*.
#' *3009002*, *3009004* are the most popular colors of our product _black_ and _blue_ respectivly.
#' Retailers feedback sugests that new _dark gray_ color is havly demand, but unavaliable from our manufacture.

#' Experts consensus suggests that introduction of new *3009013* _dark gray_ product will
#' increase market share of related colors (_black_, _blue_ and new _dark gray_) in that product family about 15%.
#' In addition it will take over about
#' - 25% of *3009002* _black_ demands,
#' - 40% of *3009004* _blue_ demands
#' of existing products, becoming the most popular color in family.

#' ## Calculation
#' New color will be shipped at 2018/09/01
#' forecast 60 days from release will be more than suficient.

#' fetch current demand forecast for 3009002 (black)
blackDemand <- demand.forecast.current(
    refNo = "3009002",
    from = as.Date("2018/09/01"),
    to = as.Date("2018/10/30")
)
#' fetch current demand forecast for 3009004 (blue)
blueDemand <- demand.forecast.current(
    refNo = "3009004",
    from = as.Date("2018/09/01"),
    to = as.Date("2018/10/30")
)

#' Experts provided calculation
expectedDemand <- (blackDemand$level + blueDemand$level) * 0.15 + blackDemand$level * 0.25 + blueDemand$level * 0.40

darkgrayDemand <- data.frame(
    refNo = rep(productRefNo, 60),
    date = blackDemand$date,
    level = expectedDemand
)

#' Adaptation of existing products demand only for presentation purpose
blackDemand$level <- blackDemand$level * 0.25
blueDemand$level <- blueDemand$level * 0.40

#' ## Results
plot(darkgrayDemand[, c("date", "level")], type = "l", col = "darkgray", lty = 1)
lines(blueDemand[, c("date", "level")], type = "l", col = "blue", lty = 2)
lines(blackDemand[, c("date", "level")], type = "l", col = "black", lty = 2)
legend(0, 0,
    legend = c(productRefNo, "3009002 (black)", "3009004 (blue)"),
    col = c("darkgray", "black", "blue"),
    lty = c(1,2,2), ncol=1
)

#' Demand forecast saved alongside report generation:
darkgrayDemand
demand.forecast.new(darkgrayDemand)
