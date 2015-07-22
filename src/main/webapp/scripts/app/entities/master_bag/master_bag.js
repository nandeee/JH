'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('master_bag', {
                parent: 'entity',
                url: '/master_bags',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_bag.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_bag/master_bags.html',
                        controller: 'Master_bagController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_bag');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('master_bag.detail', {
                parent: 'entity',
                url: '/master_bag/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_bag.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_bag/master_bag-detail.html',
                        controller: 'Master_bagDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_bag');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Master_bag', function($stateParams, Master_bag) {
                        return Master_bag.get({id : $stateParams.id});
                    }]
                }
            })
            .state('master_bag.new', {
                parent: 'master_bag',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/master_bag/master_bag-dialog.html',
                        controller: 'Master_bagDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {code: null, creationTime: null, handoverTime: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('master_bag', null, { reload: true });
                    }, function() {
                        $state.go('master_bag');
                    })
                }]
            })
            .state('master_bag.edit', {
                parent: 'master_bag',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/master_bag/master_bag-dialog.html',
                        controller: 'Master_bagDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Master_bag', function(Master_bag) {
                                return Master_bag.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('master_bag', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
