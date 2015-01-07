'use strict';

define([ 'angular', './controllers/cfCtrl' ], function(angular, cfCtrl) {

	/* Controllers */
	var module = angular.module('fluffyCloud.cf.controllers', []);
	module.controller('CFCTRL', cfCtrl);
	return module;
});
