using CryptoNews.Models;
using SQLite;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CryptoNews.Database
{
    public class CryptoNewsDB
    {
        SQLiteConnection database;

        //Database Initialization
        public CryptoNewsDB(string dbPath)
        {
            database = new SQLiteConnection(dbPath);
            database.CreateTable<BlogWebsite>();
            database.CreateTable<BlogPost>();
            InitializeSeedData();
        }

        #region BlogWebsites
        public List<BlogWebsite> GetAllBlogWebsites()
        {
           return database.Table<BlogWebsite>().ToList();
          
        }

        public async Task<int> AddBlogWebsite(List<BlogWebsite> blogWebsites)
        {
            database.DropTable<BlogPost>();
            database.CreateTable<BlogPost>();
            return database.Insert(blogWebsites);
        }

        public async Task<int> DeleteBlogWebsite(BlogWebsite blogWebsite)
        {
            return database.Delete(blogWebsite);
        }
        #endregion

        #region BlogPosts
        public async Task<List<BlogPost>> GetAllBlogPosts()
        {
            return database.Table<BlogPost>().ToList();
        }

        public async Task<int> AddUpdatedBlogPosts(List<BlogPost> blogPosts)
        {
            //var DatabaseBlogPosts = database.Table<BlogPost>().ToList();
            //foreach (var blogPost in blogPosts)
            //{
            //    if (DatabaseBlogPosts.Find(s => s.Id == blogPost.Id) == null)
            //    {
            //        database.Insert(blogPost);
            //    }
            //}

            database.DropTable<BlogPost>();
            database.CreateTable<BlogPost>();
            return database.InsertAll(blogPosts);           
        }
#endregion

        public  async Task InitializeSeedData()
        {
            var SampleblogWesite1 = new BlogWebsite
            {
                Name = "CryptoClarified",
                Url = "https://cryptoclarified.com"
            };
              var SampleblogWesite2 = new BlogWebsite
              {
                  Name = "CCN",
                  Url = "https://ccn.com/"
              };
           
            database.Insert(SampleblogWesite1);
            database.Insert(SampleblogWesite2);
            //database.Insert(SampleblogWesite3);
        }


    }
}
