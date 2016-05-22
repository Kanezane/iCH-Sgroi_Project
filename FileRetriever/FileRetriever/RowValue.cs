namespace FileRetriever {

    public class RowValue {
        private string title;
        private string url;

        public RowValue(string title, string url) {
            this.title = title;
            this.url = url;
        }
        
        public string getTitle () {
            return title.Replace("&amp;", "&");
        }

        public string getUrl () {
            return url;
        }
    }
}