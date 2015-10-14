angular.module('MyApp')
  .factory('Account', function($http) {
    return {
      getProfile: function() {
        return $http.get('/profile');
      },
      updateProfile: function(profileData) {
        return $http.put('/profile', profileData);
      }
    };
  });