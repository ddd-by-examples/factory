Feature: Production planning

  // https://github.com/michal-michaluk/production/tree/master

  sub domain: production planning
  Keep track of correct production plan
  to ensure sufficient capacity
  and high utilization of resources in tune with forecast demand

  Domain story:
  Production plan schedule new production on line, using form, at time for duration.
  >> productionPlan.scheduleNewProduction(line, form, time, duration)
  Only if form will be available at the time for whole duration, production may by planned.
  Production includes retooling time, parts output of used form and utilization of human resources
  on that production stage.
  If production on given line overlaps with other: preceding must shrink,
  succeeding one must start later.
  If consecutive productions use same from, retooling time between is zero. [not mentioned prev.]
  Production forecasts (parts output * production duration) changes new and changed productions.
  Overall utilization of human resources increases.
  Shortage may arise if insufficient production was planned.


  Production plan adjust production to time and duration.
  >> productionPlan.adjust(production, time, duration)
  If production on given line overlaps with other: preceding must shrink,
  succeeding one must start later.
  If consecutive productions use same from, retooling time between is zero.
  Production forecasts (parts output * production duration) changes for changed productions.
  Overall utilization of human resources may change.
  Shortage may arise if insufficient production was planned.

  Scenario: schedule new production consecutive production must shrink
    Given plan for next monday:
      | line | beginAt | duration | productRefNo |
      | 1    | 6:00    | 3.5h     | 3009000      |
    When new production is scheduled for next monday:
      | line | beginAt | duration | productRefNo |
      | 1    | 8:00    | 2.0h     | 4400800      |
    Then demand is changed
      | line | beginAt | duration | productRefNo |
      | 1    | 6:00    | 2.0h     | 3009000      |
      | 1    | 8:00    | 2.0h     | 4400800      |
    Then current production outputs for "3009000" are
      | 2000 | 0 | 0 | 0 | 0 | 0 | 0 |
    Then current production outputs for "4400800" are
      | 3500 | 0 | 0 | 0 | 0 | 0 | 0 |
    Then no shortage was found

  Scenario: schedule new production succeeding productions must start later
    Given plan for next monday:
      | line | beginAt | duration | productRefNo |
      | 1    | 6:00    | 2.0h     | 3009000      |
      | 1    | 9:30    | 3.5h     | 3009000A     |
    When new production is scheduled for next monday:
      | line | beginAt | duration | productRefNo |
      | 1    | 8:00    | 2.0h     | 4400800      |
    Then demand is changed
      | line | beginAt | duration | productRefNo |
      | 1    | 6:00    | 2.0h     | 3009000      |
      | 1    | 8:00    | 2.0h     | 4400800      |
      | 1    | 10:00   | 3.5h     | 3009000A     |
    Then current production outputs for "3009000" are
      | 2000 | 0 | 0 | 0 | 0 | 0 | 0 |
    Then current production outputs for "3009000A" are
      | 2000 | 0 | 0 | 0 | 0 | 0 | 0 |
    Then current production outputs for "4400800" are
      | 3500 | 0 | 0 | 0 | 0 | 0 | 0 |
    Then no shortage was found

  Scenario: schedule new production consecutive production must shrink and succeeding must start later
    Given plan for next monday:
      | line | beginAt | duration | productRefNo |
      | 1    | 6:00    | 3.5h     | 3009000      |
      | 1    | 9:30    | 3.5h     | 3009000A     |
    When new production is scheduled for next monday:
      | line | beginAt | duration | productRefNo |
      | 1    | 8:00    | 2.0h     | 4400800      |
    Then demand is changed
      | line | beginAt | duration | productRefNo |
      | 1    | 6:00    | 2.0h     | 3009000      |
      | 1    | 8:00    | 2.0h     | 4400800      |
      | 1    | 10:00   | 3.5h     | 3009000A     |
    Then current production outputs for "3009000" are
      | 2000 | 0 | 0 | 0 | 0 | 0 | 0 |
    Then current production outputs for "3009000A" are
      | 2000 | 0 | 0 | 0 | 0 | 0 | 0 |
    Then current production outputs for "4400800" are
      | 3500 | 0 | 0 | 0 | 0 | 0 | 0 |
    Then no shortage was found
