using CryptoNews.Models;
using CryptoNews.Services;
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
            database.CreateTable<FavouriteBlogPost>();
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
            var posts = database.Table<BlogPost>().ToList();
            foreach(var post in posts)
            {
                post.PrettyDate = PrettyDate.GetPrettyDate(post.Date);
            }
            return posts;
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

        #region FavouriteBlogPosts
        public List<FavouriteBlogPost> GetAllFavouriteBlogPosts()
        {
            var FavouritePosts = database.Table<FavouriteBlogPost>().OrderBy(x => x.Date).ToList();
            foreach (var post in FavouritePosts)
            {
                post.PrettyDate = PrettyDate.GetPrettyDate(post.Date);
            }
            return FavouritePosts;
        }

        public async  Task<int> AddFavouriteBlogPost(FavouriteBlogPost blogPost)
        {
            var DatabaseBlogPosts =  database.Table<FavouriteBlogPost>().ToList();
              if (DatabaseBlogPosts.Find(s => s.Id == blogPost.Id) == null)
                {
                    return database.Insert(blogPost);
                }

            return database.Insert(blogPost);
        }

        public int DeleteFavouriteBlogPost(FavouriteBlogPost blogPost)
        {
            return database.Delete(blogPost);
        }
        #endregion



    }
}
