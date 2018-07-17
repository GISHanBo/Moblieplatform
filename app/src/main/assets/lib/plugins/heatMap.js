/*
*
* Leaflet divHeatmap Layer
* Lightweight heatmap implementation using divIcons and CSS3 radial gradients
* 
* */


L.DivHeatmapLayer = L.FeatureGroup.extend({
  options: {
    radius: 10
  },

  initialize: function(options){
    L.Util.setOptions(this, options);
    this._layers = {};

    // Define CSS rule for making heatmap invisible to clicking
    var style = document.createElement('style');
    document.head.appendChild(style);

    // Append CSS rules
    //style.appendChild(document.createTextNode(''));
    //var sheet = style.sheet;
    //sheet.insertRule(".leaflet-heatmap-blob { transition: width 1s, height 1s; }", 0);
  },

  _addBlob: function(lat,lng,value,old_marker){
    // Remove previous
    if (typeof old_marker != 'undefined') {
      this.removeLayer(old_marker);
    }

    if ( (!value && value != 0) || (!lat && lat != 0) || (!lng && lng != 0)) {
      throw new Error('Provide a latitude, longitude and a value');
    }

    if (value > 1 || value < 0) {
      throw new Error('Value should be beetween 0 and 1');
    }

    // Define the marker
    var zIndexOffset=3;
    var gradient =  'radial-gradient(closest-side, rgba(255,0,0, 1), rgba(255,0,0,0.4))';
    if(0.3<value&&value<=0.6){
    	zIndexOffset=2;
    	gradient =  'radial-gradient(closest-side, rgba(255,255,0, 1), rgba(255,255,0,0.3))';
    }else if(value<=0.3){
    	zIndexOffset=1;
    	gradient =  'radial-gradient(closest-side, rgba(0,0,255, 1), rgba(0,0,255,0.4))';
    }
    var html = '<div class="heatblob" data-value="'+value+'" style="width:100%;height:100%;border-radius:50%;background-image:'+gradient+'">';
    var size = this.options.radius;
    var divicon = L.divIcon({
      iconSize: [ size, size ],
      className: 'leaflet-heatmap-blob',
      html: html
    });

    var marker = L.marker([lat, lng], {
      icon: divicon,
      clickable: false,
      keyboard: false,
      opacity: value,
      zIndexOffset:zIndexOffset
    }).addTo(this);

    return marker;
  },

  _dataset: [],
  _markerset: [],

  setData: function(data) {
    // Data object is three values [ {lat,lon,value}, {...}, ...]
    this.clearData();
    var self = this;
    data.forEach(function(point){
      point.value = point.value > 1 ? 1 : point.value;
      var marker = self._addBlob(point.lat,point.lon,point.value);
      self._markerset.push(marker);
      self._dataset.push({
        "lat": point.lat,
        "lon": point.lon,
        "value": point.value
      });

    });
  },

  getData: function() {
    return this._dataset;     
  },

  clearData: function() {
    this.clearLayers();
    this._markerset = [];
    this._dataset = [];
  },


  _animateBlob: function(lat,lng,start_value,end_value,marker,fadeIn,fadeOut) {
    var self = this;
    if (!marker) var marker;

    var v = start_value;
    var delay = 50; // millis
    var step = start_value < end_value ? 0.1 : -0.1;

    var seed = setInterval(function() {
      //if (!marker) self.removeLayer(marker);
      v = v + step;
      // Gate values so that the blob is always correct during progression
      v < 0 ? v = 0 : v > 1 ? v = 1 : v = v;
      marker = self._addBlob(lat,lng,v,marker);
      if (v >= Math.max(start_value,end_value) || v <= Math.min(start_value,end_value)) {
        //console.log(lat,lng,v,marker);
        window.clearInterval(seed);
        if (v <= Math.min(start_value,end_value)) {
          if (fadeOut) fadeOut(marker);
        }
        if (v >= Math.max(start_value,end_value)) {
          if (fadeIn) fadeIn(marker);
        }
      };
    },delay);

  },

  morphData: function(new_data) {
    this.fadeOutData();
    this.fadeInData(new_data);
  },

  fadeInData: function (data) {
    var self = this;
    data.forEach(function(point){
      point.value = point.value > 1 ? 1 : point.value;
      self._animateBlob(point.lat,point.lon,0,point.value,null,function fadeIn(marker) {
                self._markerset.push(marker);
        self._dataset.push({
          "lat": point.lat,
          "lon": point.lon,
          "value": point.value
        });
      });
    })
  },

  fadeOutData: function() {
    var self = this;
    var qty = self._dataset.length;
    for (var i =0; i < qty; i++) {
      var point = self._dataset.pop();
      var marker = self._markerset.pop();
      self._animateBlob(point.lat,point.lon,point.value,0,marker,null,function fadeOut(marker) {
        self.removeLayer(marker);
      });
    }
  },


  /*
  * Testing
  */
  testRandomData: function(number) {
    var data = [];
    var count = number || 100;
    while (count-- > 0) {
      var value = Math.random();	// 0 - 1 (opacity)
      var lat = 90 * Math.random();
      var lng = 180 * Math.random();
      data.push({
        "lat": lat,
        "lon": lng,
        "value": value
      });
    }
    return data;
  },

  testAddPoints: function(number) {
    var count = number || 100;
    while (count-- > 0) {
      var value = Math.random();	// 0 - 1 (opacity)
      var lat = 90 * Math.random();
      var lng = 180 * Math.random();
      this._addBlob(lat,lng,value);
    }
  },

  testAddData: function(number) {
    this.clearData();
    this.setData(this.testRandomData(number));
  },

  testAnimatePoints: function(number) {
    this.clearData();
    var self = this;
    var data = this.testRandomData(number);
    data.forEach(function(point) {
      self._animateBlob(point.lat,point.lon,0,point.value);
    });
  },

  testMorphData: function(number) {
    this.clearData();
    this.setData(this.testRandomData(number));
    this.morphData(this.testRandomData(number));
  },

 });

