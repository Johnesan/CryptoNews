using SQLite;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CryptoNews.Models
{
    public class BlogWebsite
    {
        [PrimaryKey, AutoIncrement]
        public int AppId { get; set; }
        public string Name { get; set; }
        public string Url { get; set; }
        public string Logo { get; set; }
    }
}
