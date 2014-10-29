'use strict';

define([ 'angular', './controller/vpcCtrl' ], function(angular, vpcCtrl) {

	/* Controllers */

	var module = angular.module('fluffyCloud.vpc.controllers', []);

	module.controller('VPCCTRL', vpcCtrl);

	return module;
});
