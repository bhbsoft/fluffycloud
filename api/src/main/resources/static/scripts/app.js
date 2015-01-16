'use strict';

define([ 'angular', 'angularAnimate', 'uiRouter', 'angularBootstrap', 'angularFileUpload', 'toaster', './ec2/module',
		'./cloudformation/module' ], function(angular, angularAnimate, uiRouter, uibootstrap, toaster, ec2, cf) {

	return angular.module('fluffyCloud', [ 'ui.router', 'ui.bootstrap', 'toaster',
			'fluffyCloud.ec2', 'fluffyCloud.cf', 'ngAnimate' ]);
});
