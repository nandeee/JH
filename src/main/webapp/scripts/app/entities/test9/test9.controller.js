'use strict';

angular.module('jhipsterApp')
    .controller('Test9Controller', function ($scope, Test9) {
        window.scope = $scope;
        $scope.test9s = [];
        $scope.loadAll = function() {
            Test9.get(function(result) {
               $scope.test9s = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Test9.get({id: id}, function(result) {
                $scope.test9 = result;
            });
        };

        $scope.save = function () {
            if ($scope.test9.id != null) {
                Test9.update($scope.test9,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Test9.save($scope.test9,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.edit = function (entity) {
            $scope.test9 = entity;
            $scope.showForm = true;
        };

        $scope.delete = function (id) {
            Test9.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.test9 = {name: null, age: null, date: null, time: null, id: null};

        $scope.clear = function () {
            $scope.test9 = {name: null, age: null, date: null, time: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();

        };

        $scope.gridOptions = {
            data: 'test9s',
            enableFiltering: true,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                    Test9.update(rowEntity,
                        function() {
                            $scope.refresh();
                        });
                });
            },
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true

            }, {
                field: "name",
                displayName: "name",
                editableCellTemplate: '<div> <form name="inputForm"> <input type="text" required validate-required-cell minlength=0 maxlength=20 pattern=^[a-zA-Z0-9]*$ ng-class="\'colt\' + col.uid" ui-grid-editor ng-model="MODEL_COL_FIELD" /> </form></div>'

            }, {
                field: "age",
                displayName: "age",
                editableCellTemplate: '<div> <form name="inputForm"> <input type="number" validate-required-cell required min=0 max=100 ng-class="\'colt\' + col.uid" ui-grid-editor ng-model="MODEL_COL_FIELD" /> </form></div>'

            }, {
                field: "date",
                displayName: "date",
                editableCellTemplate: '<div> <form name="inputForm"> <input type="date" validate-required-cell ng-class="\'colt\' + col.uid" ui-grid-editor ng-model="MODEL_COL_FIELD" /> </form></div>'

            }, {
                field: "time",
                displayName: "time",
                editableCellTemplate: '<div> <form name="inputForm"> <input type="datetime-local" validate-required-cell required ng-class="\'colt\' + col.uid" ui-grid-editor ng-model="MODEL_COL_FIELD" /> </form></div>'


            }, {
                name: 'placeholder',
                displayName: '',
                width: 150,
                enableSorting: false,
                enableCellEdit: false,
                enableFiltering: false,
                headerCellTemplate: '<span></span>',
                cellClass: 'tableButton',
                cellTemplate: '<div class="tableButton"><button id="delBtn" type="button" class="btn-small" ng-click="grid.appScope.delete(row.entity.id)">Delete</button><button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.edit(row.entity)">Edit</button></div>'
            }],
        };

        $scope.ctrl = {};
        $scope.ctrl.querySearch = querySearch;

        function querySearch(query, entity) {
            var results = query ? $scope[entity].filter(createFilterFor(query)) : $scope[entity];
            return results;
        }

        function createFilterFor(query) {
            var lowercaseQuery = angular.lowercase(query);
            return function filterFn(state) {
                return (state.name.toLowerCase().indexOf(lowercaseQuery) === 0);
            };
        }
    });
