Feature: manual adjustments of demand

  Sub Domain: demand forecasting
  keeps track of current and future customer needs for our products

  Domain story:
  Adjust demand at day to amount, delivered.
  We can change only Demands for today and future.

  New demand is stored for further reference
  Data from call-off document should be preserved.
  Adjust demand should be possible even
  if there was no document for that product.
  In standard case future call-off documents should override adjustment,
  but if customer warn us about opposite case
  import of document should not remove previous adjustments.
  Logistician note should be kept with adjustment.

  Domain event: demanded levels changed
  [context boundary]

  Sub Domain: shortage prediction
  continuously monitors demands, production plan and stock levels
  predicts potential shortage based on forecasts
  notifies personal about potential shortages

  If new demand is not fulfilled by
  current product stock and production forecast
  there is a shortage in particular days and we need to rise an alert.
  planner should be notified,

  if there are locked parts on stock,
  QA task for recovering them should have high priority.

  Scenario: demand increased but fulfilled by current stock level
    Given demand for product refNo "3009000":
      | 0 | 100 | 300 | 0 | 0 | 0 | 0 |
    When demand of refNo "3009000" is adjusted for "tomorrow" to 400
    Then demand is changed to 400
    Then current demands are
      | 0 | 400 | 300 | 0 | 0 | 0 | 0 |
    Then no shortage was found
    Given current stock of proper parts of refNo "3009000" is 750

  Scenario: demand increased, shortage is found
    Given demand for product refNo "3009000":
      | 0 | 100 | 300 | 0 | 0 | 0 | 0 |
    Given no production of refNo "3009000" is planned
    Given current stock of proper parts of refNo "3009000" is 350
    When demand of refNo "3009000" is adjusted for "tomorrow" to 400
    Then demand is changed
    Then shortage of 250 parts for "day after tomorrow" is found
    Then current demands are
      | 0 | 400 | 300 | 0 | 0 | 0 | 0 |

  Scenario: demand increased, but stock forecast including production will be enough
    Given demand for product refNo "3009000":
      | 0 | 100 | 300 | 0 | 0 | 0 | 0 |
    Given planned production of refNo "3009000":
      | 300 | 0 | 0 | 0 | 0 | 0 | 0 |
    Given current stock of proper parts of refNo "3009000" is 450
    When demand of refNo "3009000" is adjusted for "tomorrow" to 400
    Then demand is changed
    Then no shortage was found
    Then current demands are
      | 0 | 400 | 300 | 0 | 0 | 0 | 0 |

  Scenario: next call-off will be stronger than adjustment
    Given demand for product refNo "3009000":
      | 0 | 100 | 0 | 0 | 0 | 0 | 0 |
    When demand of refNo "3009000" is adjusted for "tomorrow" to 400
    Then demand is changed
    When new call-off document for product refNo "3009000" contains:
      | 0 | 100 | 0 | 0 | 0 | 0 | 0 |
    Then demand is not changed
    Then current demands are
      | 0 | 100 | 0 | 0 | 0 | 0 | 0 |

  Scenario: in some cases adjustment will be stronger that future call-off
    Given demand for product refNo "3009000":
      | 0 | 100 | 0 | 0 | 0 | 0 | 0 |
    When demand of refNo "3009000" is strongly adjusted for "tomorrow" to 400
    Then demand is changed
    When new call-off document for product refNo "3009000" contains:
      | 0 | 100 | 0 | 0 | 0 | 0 | 0 |
    Then demand is changed
    Then current demands are
      | 0 | 400 | 0 | 0 | 0 | 0 | 0 |

  Scenario: strong adjustment will be reported if new call-off contains other value then previous
    Given demand for product refNo "3009000":
      | 0 | 100 | 0 | 0 | 0 | 0 | 0 |
    When demand of refNo "3009000" is strongly adjusted for "tomorrow" to 400
    Then demand is changed
    When new call-off document for product refNo "3009000" contains:
      | 0 | 600 | 0 | 0 | 0 | 0 | 0 |
    Then review request is shown on report [demand for refNo "3009000" was 100, adjusted to 400, but call-off has 600]
    Then demand is not changed
    Then current demands are
      | 0 | 400 | 0 | 0 | 0 | 0 | 0 |
