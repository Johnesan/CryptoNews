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
        }

        #region BlogWebsites
        public List<BlogWebsite> GetAllBlogWebsites()
        {
           return database.Table<BlogWebsite>().ToList();
          
        }

        public  int AddBlogWebsite(BlogWebsite blogWebsite)
        {

            return database.Insert(blogWebsite);
        }

        public int UpdateBlogWebsite(BlogWebsite blogWebsite)
        {

            return database.Update(blogWebsite);
        }

        public async void ResetBlogWebsite()
        {
            database.DropTable<BlogWebsite>();
            database.CreateTable<BlogWebsite>();
            await App.InitializeSeedData();
           
        }
        #endregion

        #region BlogPosts
        public List<BlogPost> GetAllBlogPosts()
        {
            return database.Table<BlogPost>().ToList();
        }

        public int AddUpdatedBlogPosts(List<BlogPost> blogPosts)
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

       

    }
}
