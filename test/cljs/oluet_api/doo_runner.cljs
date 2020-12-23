(ns oluet-api.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [oluet-api.core-test]))

(doo-tests 'oluet-api.core-test)

