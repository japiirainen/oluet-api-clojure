(ns oluet-api.routes.services.graphql
  (:require
    [com.walmartlabs.lacinia.util :refer [attach-resolvers]]
    [com.walmartlabs.lacinia.schema :as schema]
    [com.walmartlabs.lacinia :as lacinia]
    [clojure.data.json :as json]
    [clojure.edn :as edn]
    [clojure.java.io :as io]
    [ring.util.http-response :refer :all]
    [mount.core :refer [defstate]]
    [oluet-api.db.core :as conn]))


(defn get-hero [context args value]
  (let [data  [{:id 1000
               :name "Luke"
               :home_planet "Tatooine"
               :appears_in ["NEWHOPE" "EMPIRE" "JEDI"]}
              {:id 2000
               :name "Lando Calrissian"
               :home_planet "Socorro"
               :appears_in ["EMPIRE" "JEDI"]}]]
           (first data)))

(defn create_user [context args value]
  (println args)
  (let [fields {:id 1 :first_name "joona" :last_name "poona" :email "foo@bar.com" :pass "123"}]
    (conn/create-user! fields))
  {:id 1 :first_name "joona" :last_name "poona" :email "foo@bar.com" :pass "123"}
  )

(defn get-user [context args value]
  {:id 1 :first_name "joona" :last_name "poona" :email "foo@bar.com" :pass "123"})

(defstate compiled-schema
  :start
  (-> "graphql/schema.edn"
      io/resource
      slurp
      edn/read-string
      (attach-resolvers {:get-hero get-hero
                         :get-droid (constantly {})
                         :get-user get-user
                         :create-user create_user})
      schema/compile))

(defn format-params [query]
   (let [parsed (json/read-str query)] ;;-> placeholder - need to ensure query meets graphql syntax
     (str "query { hero(id: \"1000\") { name appears_in }}")))

(defn execute-request [query]
    (let [vars nil
          context nil]
    (-> (lacinia/execute compiled-schema query vars context)
        (json/write-str))))
