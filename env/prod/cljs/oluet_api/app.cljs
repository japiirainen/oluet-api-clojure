(ns oluet-api.app
  (:require [oluet-api.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
