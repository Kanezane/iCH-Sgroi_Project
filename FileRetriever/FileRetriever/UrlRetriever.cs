using System;
using System.Collections.Generic;
using System.Text;
using HtmlAgilityPack;
using System.Net;
using System.Windows.Forms;

namespace FileRetriever {

    class UrlRetriever : IUrlRetriever {

        public List<RowValue> retrieve(string contentToSearch, int queryCount) {
            List<RowValue> result = new List<RowValue>();

            if (isEmpty(contentToSearch) == false) {
                HtmlAgilityPack.HtmlDocument html = extractGoogleSource(contentToSearch, queryCount);

                foreach (HtmlNode link in html.DocumentNode.SelectNodes("//a[@href]")) {
                    string hrefValue = link.GetAttributeValue("href", string.Empty);

                    if (!hrefValue.ToString().ToLower().Contains(".googleusercontent.")
                      && hrefValue.ToString().Contains("/url?q=")
                      && (hrefValue.ToString().ToLower().Contains("http://")
                        || hrefValue.ToString().ToLower().Contains("https://"))) {

                        int index = hrefValue.IndexOf("&");
                        if (index > 0) {
                            hrefValue = hrefValue.Substring(0, index);
                            result.Add(new RowValue(link.InnerText, UrlDecode(hrefValue)));
                        }
                    }
                }
            }
            return result; 
        }

        private HtmlAgilityPack.HtmlDocument extractGoogleSource(string contentToSearch, int queryCount) {
            HttpWebRequest request = (HttpWebRequest)WebRequest
                                                        .Create("http://google.com/search?q="
                                                               + contentToSearch.Trim()
                                                               + "streaming download"
                                                               + "&num=" + queryCount);
            HttpWebResponse response = (HttpWebResponse)request.GetResponse();

            StringBuilder sb = new StringBuilder();
            string tempString;
            byte[] ResultsBuffer = new byte[8096];
            int count = 0;
            do {
                count = response.GetResponseStream().Read(ResultsBuffer, 0, ResultsBuffer.Length);
                if (count != 0) {
                    tempString = Encoding.ASCII.GetString(ResultsBuffer, 0, count);
                    sb.Append(tempString);
                }
            } while (count > 0);

            HtmlAgilityPack.HtmlDocument html = new HtmlAgilityPack.HtmlDocument();
            html.OptionOutputAsXml = true;
            html.LoadHtml(sb.ToString());
            return html;
        }

        private String UrlDecode(String s) {
            return Uri.UnescapeDataString(s.Replace("/url?q=", ""));
        }

        private Boolean isEmpty(String textboxContent) {
            if (textboxContent == "") {
                MessageBox.Show("Devi inserire il valore!");
                return true;
            } else {
                return false;
            }
        }
    }
}
