/**
 * Created by Romain on 02/10/2016.
 */

var user = null;
$(document).ready(function () {
    console.log("document ready");
    app.loadTemplates(["loginView","mapView","mapTableView","navBarView"],
        function () {
            app.router = new app.Router();
            Backbone.history.start();
        });
});


