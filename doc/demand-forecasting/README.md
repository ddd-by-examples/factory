
# Demand forecasting
Demand forecasting refers to predicting the sales of the product. 

Demand forecasting, enables organization to take various business decision on operational level, such as:
- production planning,
- purchasing of materials,
- shortage prediction,
- product pricing,
- inventory optimization,
- business risk management.

But to benefit from being demand-driven, demanded values need to be predicted, collected and kept accurate according to dynamic conditions.

There is plenty of demand predicting approaches:
- Qualitative approaches:
  - Judgemental (surveys, specialist opinion, consensus methods)
  - Experimental (test marketing, customer buying database, customer panels)
- Quantitative:
  - Relationship (econometric models, life cycle models, input-output models)
  - Time Series (moving averages, exponential smoothing, threshold methods, GARCH, adaptive models)
- Scenario Modeling: refining quantitative forecast with specific adjustments reflecting upcoming situation.

## Continuous demand forecasting
Continuous demand forecasting refers to automated, continuous prediction no high granularity like demand for product-day-location.
Continuous calculation involve predictive modeling, algorithmic modeling, pattern identification, scenario modeling and simulations by combining selected predicting approaches depending on demands mathematical properties.
![Continuous demand forecasting](continuous-forecasting.gif)

## Framework agreement and call‐off orders
Demands information can be collected based on framework agreement and call‐off orders.
In case of high volume production of low value consumables or materials, an framework agreement with customer specifies contracted demand (on annual, quarterly or monthly granularity).
In addition call‐off order’s contains commitment values in short period of time with higher granularity like product-day-location or product-week-location.
Call-off orders and be actualized continuous even on daily basis replacing need for continuous demand forecasting.
![Framework agreement and call‐off orders](call-of-orders.gif)

## Custom orders
For custom goods ordered in advance, order based demand can replace demand forecasting.
In case of lack of neither continuous demand forecasting nor call‐off orders, customer orders can be treated as product-day-location demand so any further processing requiring demand information may work normally.
![Custom orders](custom-orders.gif)

## Manual management
Ad-hoc adjustments of demands on top of any existing forecasts.

## Propagation of demand
![Propagation of demand](demand-propagation.gif)
