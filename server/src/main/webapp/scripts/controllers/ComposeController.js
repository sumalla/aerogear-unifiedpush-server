/*
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
'use strict';

angular.module('newadminApp').controller('ComposeController', function($rootScope, $scope, $routeParams, $window, $modal, pushApplication, Notifications) {

    /*
     * INITIALIZATION
     */
  $scope.variantSelection = [];
  $scope.variantSelected = false;
  $scope.criteria = [];

  pushApplication.get( {appId: $routeParams.applicationId}, function ( application ) {
    $scope.application = application;
    var href = $window.location.href;
    $scope.currentLocation = href.substring( 0, href.indexOf( '#' ) );
  } );

  $scope.sendMessage = function () {
    var pushData = {'message': {'sound': 'default', 'alert': $scope.testMessage}};

    //let's check if we filter variants
    pushData.variants = $scope.variantSelection;

    //let's check if we filer on aliases
    if($scope.criteria.alias) {
      pushData.alias = $scope.criteria.alias.split(",");
    }

    //let's check if we filter on deviceType
    if($scope.criteria.deviceType) {
      pushData.deviceType = $scope.deviceType.alias.split(",");
    }

    //let's check if we filter on categories
    if($scope.criteria.categories) {
      pushData.categories = $scope.categories.alias.split(",");
    }

    $.ajax
      ({
      contentType: 'application/json',
      type: 'POST',
      url: 'rest/sender',
      username: $scope.application.pushApplicationID,
      password: $scope.application.masterSecret,
      data: JSON.stringify( pushData ),
      success: function(){
          Notifications.success('Successfully sent Notification');
      },
      error: function(jqXHR, textStatus, error){
          Notifications.error('Something went wrong...', 'danger');
      },
      complete: function () {
           $scope.testMessage = " ";
           $scope.$apply()
      }
    });
  };

    $scope.changeVariant = function (application) {
        var modalInstance = show(application, 'filter-variants.html');
        modalInstance.result.then(function (application) {

        });
    };

    $scope.changeCriteria = function (application) {
        var modalInstance = show(application, 'add-criteria.html');
        modalInstance.result.then(function (application) {

        });
    };

    function modalController($scope, $modalInstance, application, variantSelection, criteria, variantSelected) {

        $scope.variantSelection = variantSelection;
        $scope.criteria = criteria;
        $scope.application = application;
        $scope.variantSelected = variantSelected;
        $scope.ok = function (application) {
            $modalInstance.close(application);
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        $scope.toggleSelection = function toggleSelection(variant) {
            var idx = $scope.variantSelection.indexOf(variant.variantID);

            // is currently selected
            if (idx > -1) {
                $scope.variantSelection.splice(idx, 1);
            }
           // is newly selected
            else {
                $scope.variantSelection.push(variant.variantID);
            }

        };
    }


    function show(application, template) {
        return $modal.open({
            templateUrl: 'views/dialogs/' + template,
            controller: modalController,
            resolve: {
                application: function () {
                    return application;
                },
                variantSelection: function(){
                    return $scope.variantSelection
                },
                variantSelected: function(){
                    return $scope.variantSelected
                },
                criteria: function (){
                    return $scope.criteria
                }
            }
        });
    }


});
