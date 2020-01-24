(ns guestbook.routes.home
  (:require
   [guestbook.layout :as layout]
   [guestbook.db.core :as db]
   [guestbook.middleware :as middleware]
   [ring.util.http-response :as response]
   [struct.core :as st]))
(defn home-page [request]
  (layout/render
   request "home.html" {:messages (db/get-messages)}))
(defn save-message! [{:keys [params]}]
  (db/save-message! params)
  (response/found "/"))
(defn about-page [request]
  (layout/render
   request "about.html"))
(defn home-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/message" {:post save-message!}]
   ["/about" {:get about-page}]])
