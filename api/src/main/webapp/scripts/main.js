require.config({
	baseUrl : "scripts",
	paths : {
		angular : "vendor/angular/angular",
		angularUIRouter : "vendor/uiRoute/angular-ui-router",
		angularBootstrap : "vendor/ui.bootstrap/ui-bootstrap-tpls-0.10.0",
		angularTouch : 'vendor/angular/angular-touch',
		jquery : "vendor/Jquery/jquery-1.11.0",
		toaster : "vendor/toaster/toaster"

	},

	shim : {
		'angular' : {
			'exports' : 'angular'
		},
		'angularUIRoute' : [ 'angular' ],
		'angularTouch' : {
			deps : [ 'angular' ]
		},
		'angularBootstrap' : {
			deps : [ 'angular', 'angularTouch' ]
		},
		'angularUIRouter' : {
			deps : [ 'angular' ]
		}
	}

});