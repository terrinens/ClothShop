function conversionDate(timestamp) {
    if (timestamp == null) {
        return "";
    } else {
        let date = new Date(date);
        let year = date.getFullYear();
        let month = (date.getMonth() + 1).toString().padStart(2, '0');
        let day = date.getDate().toString().padStart(2, '0');
        return year + '-' + month + '-' + day;
    }
}
