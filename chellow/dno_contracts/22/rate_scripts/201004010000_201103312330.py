def lafs():
    return {
        'lv-net': {
            'winter-weekday-peak': 1.078,
            'winter-weekday-day': 1.072,
            'night': 1.067,
            'other': 1.069},
        'lv-sub': {
            'winter-weekday-peak': 1.070,
            'winter-weekday-day': 1.066,
            'night': 1.060,
            'other': 1.062},
        'hv-net': {
            'winter-weekday-peak': 1.058,
            'winter-weekday-day': 1.051,
            'night': 1.040,
            'other': 1.046}}


def tariffs():
    return {
        '510': {
            'description': 'High Voltage HH Metered',
            'gbp-per-mpan-per-day': 0.6338,
            'gbp-per-kva-per-day': 0.0163,
            'excess-gbp-per-kva-per-day': 0.0163,
            'red-gbp-per-kwh': 0.11678,
            'amber-gbp-per-kwh': 0.0003,
            'green-gbp-per-kwh': 0.00052,
            'gbp-per-kvarh': 0.00202},
        '520': {
            'description': 'High Voltage HH Metered',
            'gbp-per-mpan-per-day': 0.6338,
            'gbp-per-kva-per-day': 0.0163,
            'excess-gbp-per-kva-per-day': 0.0163,
            'red-gbp-per-kwh': 0.11678,
            'amber-gbp-per-kwh': 0.0003,
            'green-gbp-per-kwh': 0.00052,
            'gbp-per-kvarh': 0.00202},
        '521': {
            'description': 'HV Generation Intermittent',
            'gbp-per-mpan-per-day': 0.2608,
            'gbp-per-kva-per-day': 0.0163,
            'excess-gbp-per-kva-per-day': 0.00,
            'red-gbp-per-kwh': -0.00347,
            'amber-gbp-per-kwh': -0.00347,
            'green-gbp-per-kwh': -0.00347,
            'gbp-per-kvarh': 0.00106},
        '522': {
            'description': 'High Voltage Sub HH Metered',
            'gbp-per-mpan-per-day': 0.6338,
            'gbp-per-kva-per-day': 0.0119,
            'excess-gbp-per-kva-per-day': 0.0119,
            'red-gbp-per-kwh': 0.11109,
            'amber-gbp-per-kwh': 0.00013,
            'green-gbp-per-kwh': 0.00041,
            'gbp-per-kvarh': 0.00191},
        '523': {
            'description': 'HV Sub Generation Intermittent',
            'gbp-per-mpan-per-day': 0.2608,
            'gbp-per-kva-per-day': 0.00,
            'excess-gbp-per-kva-per-day': 0.00,
            'red-gbp-per-kwh': -0.00323,
            'amber-gbp-per-kwh': -0.00323,
            'green-gbp-per-kwh': -0.00323,
            'gbp-per-kvarh': 0.00082},
        '524': {
            'description': 'HV Generation Non-Intermittent',
            'gbp-per-mpan-per-day': 0.2608,
            'gbp-per-kva-per-day': 0.00,
            'excess-gbp-per-kva-per-day': 0.00,
            'red-gbp-per-kwh': -0.04964,
            'amber-gbp-per-kwh': -0.00045,
            'green-gbp-per-kwh': -0.00061,
            'gbp-per-kvarh': 0.00106},
        '525': {
            'description': 'HV Sub Generation Non-Intermittent',
            'gbp-per-mpan-per-day': 0.2608,
            'gbp-per-kva-per-day': 0.00,
            'excess-gbp-per-kva-per-day': 0.00,
            'red-gbp-per-kwh': -0.04697,
            'amber-gbp-per-kwh': -0.00034,
            'green-gbp-per-kwh': -0.00054,
            'gbp-per-kvarh': 0.00082},
        '526': {
            'description': 'LV Sub Generation Non-Intermittent',
            'gbp-per-mpan-per-day': 0.00,
            'gbp-per-kva-per-day': 0.00,
            'excess-gbp-per-kva-per-day': 0.00,
            'red-gbp-per-kwh': -0.07064,
            'amber-gbp-per-kwh': -0.00138,
            'green-gbp-per-kwh': -0.00118,
            'gbp-per-kvarh': 0.00147},
        '527': {
            'description': 'LV Generation Non-Intermittent',
            'gbp-per-mpan-per-day': 0.00,
            'gbp-per-kva-per-day': 0.00,
            'excess-gbp-per-kva-per-day': 0.00,
            'red-gbp-per-kwh': -0.07546,
            'amber-gbp-per-kwh': -0.0161,
            'green-gbp-per-kwh': -0.0132,
            'gbp-per-kvarh': 0.00169},
        '540': {
            'description': 'Low Voltage Sub HH Metered',
            'gbp-per-mpan-per-day': 0.0526,
            'gbp-per-kva-per-day': 0.0231,
            'excess-gbp-per-kva-per-day': 0.0231,
            'red-gbp-per-kwh': 0.14307,
            'amber-gbp-per-kwh': 0.00084,
            'green-gbp-per-kwh': 0.00091,
            'gbp-per-kvarh': 0.00265},
        '550': {
            'description': 'Low Voltage Sub HH Metered',
            'gbp-per-mpan-per-day': 0.0526,
            'gbp-per-kva-per-day': 0.0231,
            'excess-gbp-per-kva-per-day': 0.0231,
            'red-gbp-per-kwh': 0.14307,
            'amber-gbp-per-kwh': 0.00084,
            'green-gbp-per-kwh': 0.00091,
            'gbp-per-kvarh': 0.00265},
        '551': {
            'description': 'LV Sub Generation Intermittent',
            'gbp-per-mpan-per-day': 0.00,
            'gbp-per-kva-per-day': 0.00,
            'excess-gbp-per-kva-per-day': 0.00,
            'red-gbp-per-kwh': -0.00540,
            'amber-gbp-per-kwh': -0.00540,
            'green-gbp-per-kwh': -0.00540,
            'gbp-per-kvarh': 0.00147},
        '570': {
            'description': 'Low Voltage HH Metered',
            'gbp-per-mpan-per-day': 0.0694,
            'gbp-per-kva-per-day': 0.021,
            'excess-gbp-per-kva-per-day': 0.021,
            'red-gbp-per-kwh': 0.16146,
            'amber-gbp-per-kwh': 0.00147,
            'green-gbp-per-kwh': 0.0013,
            'gbp-per-kvarh': 0.00326},
        '580': {
            'description': 'Low Voltage HH Metered',
            'gbp-per-mpan-per-day': 0.0694,
            'gbp-per-kva-per-day': 0.021,
            'excess-gbp-per-kva-per-day': 0.021,
            'red-gbp-per-kwh': 0.16146,
            'amber-gbp-per-kwh': 0.00147,
            'green-gbp-per-kwh': 0.0013,
            'gbp-per-kvarh': 0.00326},
        '581': {
            'description': 'LV Generation Intermittent',
            'gbp-per-mpan-per-day': 0.00,
            'gbp-per-kva-per-day': 0.00,
            'excess-gbp-per-kva-per-day': 0.00,
            'red-gbp-per-kwh': -0.586,
            'amber-gbp-per-kwh': -0.586,
            'green-gbp-per-kwh': -0.586,
            'gbp-per-kvarh': 0.00169}}
