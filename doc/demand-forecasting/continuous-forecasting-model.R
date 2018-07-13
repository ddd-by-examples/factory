#' /* setup
library(FactoryAnalytics)
productRefNo <- "3009000"
#' */

#' ---
#' title: "Continuous demand forecasting"
#' author: "Michał Michaluk"
#' output: pdf_document
#' ---

# cron: 0 0 * * *
# since: 2018/05/23

#' ## Method exmplanation
#' Model based on harmonic regression which uses sines and cosines to model the seasonality of data.

#' ## Calculation
deliveries <- delivery.history(
    refNo = productRefNo
)

forecast <- NULL

#' ## Results

#' Demand forecast saved alongside report generation:
forecast
demand.forecast.new(forecast, refNo = productRefNo)
